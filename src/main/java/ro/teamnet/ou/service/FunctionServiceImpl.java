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
import ro.teamnet.ou.domain.jpa.OrganizationalUnitFunction;
import ro.teamnet.ou.mapper.FunctionMapper;
import ro.teamnet.ou.repository.jpa.AccountFunctionRepository;
import ro.teamnet.ou.repository.jpa.FunctionRepository;
import ro.teamnet.ou.repository.jpa.OrganizationalUnitFunctionRepository;
import ro.teamnet.ou.repository.jpa.OrganizationalUnitRepository;
import ro.teamnet.ou.repository.neo.FunctionNeoRepository;
import ro.teamnet.ou.web.rest.dto.FunctionDTO;

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
    private OrganizationalUnitFunctionRepository ouFunctionRepository;

    @Inject
    private AccountRepository accountRepository;

    @Inject
    private OrganizationalUnitRepository ouRepository;

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

    public FunctionDTO findOneByCode(String code) {
        Function function = functionRepository.findByCode(code);
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
        if (functionDto.getId() != null) {
            for (ro.teamnet.ou.domain.neo.Function neoFunction : functionNeoRepository.findByJpaId(functionDto.getId())) {
                neoFunction.setCode(functionDto.getCode());
                functionNeoRepository.save(neoFunction);
            }
        }
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
    public Set<FunctionDTO> findAllByAccountId(Long accountId) {
        return FunctionMapper.toDTO(accountFunctionRepository.findFunctionsByAccountId(accountId));
    }

    @Override
    public void addToAccount(Long accountId, FunctionDTO functionDTO) {
        AccountFunction accountFunction = accountFunctionRepository.findByAccountIdAndFunctionId(accountId, functionDTO.getId());
        if (accountFunction == null) {
            accountFunction = new AccountFunction();
            accountFunction.setAccount(accountRepository.findOne(accountId));
            accountFunction.setFunction(functionRepository.findOne(functionDTO.getId()));
            accountFunctionRepository.save(accountFunction);
        }
    }

    @Override
    public void removeFromAccount(Long accountId, Long functionId) {
        accountFunctionRepository.deleteByAccountIdAndFunctionId(accountId, functionId);
    }

    @Override
    public Set<FunctionDTO> findAllByOrganizationalUnitId(Long ouId) {
        return FunctionMapper.toDTO(ouFunctionRepository.findFunctionsByOrganizationalUnitId(ouId));
    }

    @Override
    public void addToOrganizationalUnit(Long ouId, FunctionDTO functionDTO) {
        Set<OrganizationalUnitFunction> ouFunctions = ouFunctionRepository.getByOrgUnitIdAndFunctionId(ouId, functionDTO.getId());
        if (ouFunctions == null || ouFunctions.size() == 0) {
            OrganizationalUnitFunction ouFunction = new OrganizationalUnitFunction();
            ouFunction.setOrganizationalUnit(ouRepository.findOne(ouId));
            ouFunction.setFunction(functionRepository.findOne(functionDTO.getId()));
            ouFunctionRepository.save(ouFunction);
        }
    }

    @Override
    public void removeFromOrganizationalUnit(Long ouId, Long functionId) {
        ouFunctionRepository.deleteByOrganizationalUnitIdAndFunctionId(ouId, functionId);
    }


}
