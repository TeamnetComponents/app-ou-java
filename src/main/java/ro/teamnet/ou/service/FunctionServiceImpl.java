package ro.teamnet.ou.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.teamnet.bootstrap.domain.Module;
import ro.teamnet.bootstrap.domain.ModuleRight;
import ro.teamnet.bootstrap.domain.util.ModuleRightTypeEnum;
import ro.teamnet.bootstrap.repository.AccountRepository;
import ro.teamnet.bootstrap.repository.ModuleRepository;
import ro.teamnet.bootstrap.service.ModuleRightService;
import ro.teamnet.bootstrap.web.rest.dto.ModuleRightDTO;
import ro.teamnet.ou.domain.jpa.AccountFunction;
import ro.teamnet.ou.domain.jpa.Function;
import ro.teamnet.ou.mapper.FunctionMapper;
import ro.teamnet.ou.repository.jpa.AccountFunctionRepository;
import ro.teamnet.ou.repository.jpa.FunctionRepository;
import ro.teamnet.ou.repository.neo.FunctionNeoRepository;
import ro.teamnet.ou.web.rest.dto.FunctionDTO;
import ro.teamnet.ou.web.rest.dto.FunctionRelationshipDTO;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Service class for managing Functions.
 */
@Service
@Transactional
public class FunctionServiceImpl implements FunctionService {

    @Inject
    private FunctionRepository functionRepository;

    @Inject
    private AccountFunctionRepository accountFunctionRepository;

    @Inject
    private AccountRepository accountRepository;

    @Inject
    private FunctionNeoRepository functionNeoRepository;

    @Inject
    private ModuleRightService moduleRightService;

    @Inject
    private ModuleRepository moduleRepository;


    public FunctionDTO findOne(Long id) {
        Function function = functionRepository.findOne(id);
        return FunctionMapper.toDTO(function);
    }

    @Override
    public Set<FunctionDTO> findAll() {
        return FunctionMapper.toDTO(functionRepository.getAllWithModuleRights());
    }

    @Override
    public FunctionDTO save(FunctionDTO functionDto) {
        Function function = (functionDto.getId() != null) ?
                FunctionMapper.updateJpa(functionRepository.findOne(functionDto.getId()), functionDto)
                : FunctionMapper.toJpa(functionDto);

        List<ModuleRight> moduleRights = new ArrayList<>();
        for (ModuleRightDTO moduleRightDTO : functionDto.getModuleRights()) {
            if (moduleRightDTO.getId() != null) {
                moduleRights.add(moduleRightService.findOne(moduleRightDTO.getId()));
            } else {
                Module module = moduleRepository.findOne(moduleRightDTO.getModule().getId());
                Short right = ModuleRightTypeEnum.READ_ACCESS.getRight();
                moduleRights.addAll(moduleRightService.findByModuleAndRight(module, right));
            }
        }
        function.setModuleRights(moduleRights);
        return FunctionMapper.toDTO(functionRepository.save(function));
    }

    @Override
    public void delete(Long id) {
        functionRepository.delete(id);
        for (ro.teamnet.ou.domain.neo.Function neoFunction : functionNeoRepository.findByJpaId(id)) {
            functionNeoRepository.delete(neoFunction);
        }
    }

    @Override
    public FunctionRelationshipDTO addRelationship(FunctionRelationshipDTO functionRelationshipDTO) {
        ro.teamnet.ou.domain.neo.Function neoFunction = FunctionMapper.toNeo(functionRelationshipDTO);
        return FunctionMapper.toDTO(functionNeoRepository.save(neoFunction));
    }

    @Override
    public void deleteRelationship(Long functionRelationshipId) {
        functionNeoRepository.delete(functionRelationshipId);
    }

    @Override
    public Set<FunctionDTO> findAllByAccountId(Long accountId) {
        return FunctionMapper.toDTO(accountFunctionRepository.findFunctionsByAccountId(accountId));
    }

    @Override
    public void addToAccount(Long accountId, FunctionDTO functionDTO) {
        AccountFunction accountFunction = new AccountFunction();
        accountFunction.setAccount(accountRepository.findOne(accountId));
        accountFunction.setFunction(functionRepository.findOne(functionDTO.getId()));
        accountFunctionRepository.save(accountFunction);
    }

    @Override
    public void removeFromAccount(Long accountId, Long functionId) {
        accountFunctionRepository.deleteByAccountIdAndFunctionId(accountId, functionId);
    }


}
