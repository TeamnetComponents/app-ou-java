package ro.teamnet.ou.service;

import org.springframework.data.neo4j.conversion.Result;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.teamnet.bootstrap.domain.Module;
import ro.teamnet.bootstrap.domain.ModuleRight;
import ro.teamnet.bootstrap.repository.ModuleRepository;
import ro.teamnet.bootstrap.service.ModuleRightService;
import ro.teamnet.ou.domain.jpa.Function;
import ro.teamnet.ou.mapper.FunctionMapper;
import ro.teamnet.ou.repository.jpa.FunctionRepository;
import ro.teamnet.ou.repository.neo.FunctionNeoRepository;
import ro.teamnet.ou.web.rest.dto.FunctionDTO;

import javax.inject.Inject;
import java.util.HashSet;
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
    private FunctionNeoRepository functionNeoRepository;

    @Inject
    private ModuleRightService moduleRightService;

    @Inject
    private ModuleRepository moduleRepository;


    public FunctionDTO findOne(Long id) {
        Function function = functionRepository.findOne(id);
        ro.teamnet.ou.domain.neo.Function functionNeo = functionNeoRepository.findByJpaId(id);
        FunctionDTO functionDTO = FunctionMapper.toDTO(function, functionNeo);
        return functionDTO;
    }


    @Override
    public FunctionDTO save(FunctionDTO functionDto) {
        Function function = FunctionMapper.toJpa(functionDto);

        for(ModuleRight mr: function.getModuleRights()) {
            if(mr.getId()==null){
                Module moduleDb=moduleRepository.findOne(mr.getModule().getId());
                mr.setModule(null);
                moduleRightService.save(mr);
                mr.setModule(moduleDb);
            }else{
                moduleRightService.save(mr);
            }

        }

        Function functionSaved = functionRepository.save(function);
        Long neoId = functionNeoRepository.findByJpaId(function.getId()).getId();
        ro.teamnet.ou.domain.neo.Function functionNeo = FunctionMapper.toNeo(functionDto);
        functionNeo.setId(neoId);
        ro.teamnet.ou.domain.neo.Function functionNeoSaved = functionNeoRepository.save(functionNeo);

        return FunctionMapper.toDTO(functionSaved,functionNeoSaved);
    }

    @Override
    public Set<FunctionDTO> findAll() {
        Set<Function> functions = functionRepository.getAllWithModuleRights();
        Result<ro.teamnet.ou.domain.neo.Function> neoFunctions = functionNeoRepository.findAll();

        Set<FunctionDTO> functionDTOSet = new HashSet<>();
        for (Function function : functions) {
            for (ro.teamnet.ou.domain.neo.Function neoFunction : neoFunctions) {
                if(function.getId() == neoFunction.getJpaId()){
                    functionDTOSet.add(FunctionMapper.toDTO(function,neoFunction));
                }
            }
        }
        return functionDTOSet;
    }

    @Override
    public void delete(Long id) {
        Function function = functionRepository.findOne(id);
        functionRepository.delete(function);
        ro.teamnet.ou.domain.neo.Function functionNeo = functionNeoRepository.findByJpaId(id);

        Long neoId = functionNeoRepository.findByJpaId(id).getId();
        functionNeo.setId(neoId);

        functionNeoRepository.delete(functionNeo);
    }

}
