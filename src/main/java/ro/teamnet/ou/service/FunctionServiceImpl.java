package ro.teamnet.ou.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.teamnet.bootstrap.domain.Module;
import ro.teamnet.bootstrap.domain.ModuleRight;
import ro.teamnet.bootstrap.domain.util.ModuleRightTypeEnum;
import ro.teamnet.bootstrap.repository.ModuleRepository;
import ro.teamnet.bootstrap.service.AbstractServiceImpl;
import ro.teamnet.bootstrap.service.ModuleRightService;
import ro.teamnet.bootstrap.web.rest.dto.ModuleRightDTO;

import ro.teamnet.ou.domain.jpa.Function;
import ro.teamnet.ou.mapper.FunctionMapper;
import ro.teamnet.ou.repository.jpa.FunctionRepository;
import ro.teamnet.ou.repository.neo.FunctionNeoRepository;
import ro.teamnet.ou.web.rest.dto.FunctionDTO;

import javax.inject.Inject;
import java.util.*;

/**
 * Service class for managing  ModuleRights.
 */
@Service
@Transactional
public class FunctionServiceImpl implements FunctionService {


    private final FunctionRepository functionRepository;

    private final FunctionNeoRepository functionNeoRepository;

    private final ModuleRightService moduleRightService;

    private final ModuleRepository moduleRepository;

    @Inject
    public FunctionServiceImpl(FunctionRepository functionRepository, ModuleRightService moduleRightService,
                               ModuleRepository moduleRepository,FunctionNeoRepository functionNeoRepository) {
        this.functionRepository = functionRepository;
        this.functionNeoRepository = functionNeoRepository;
        this.moduleRightService=moduleRightService;
        this.moduleRepository=moduleRepository;
    }


    public FunctionDTO getOneById(Long id) {
        Function function = functionRepository.getOne(id);

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

        ro.teamnet.ou.domain.neo.Function functionNeo = FunctionMapper.toNeo(functionDto);
        functionNeo.setJpaId(function.getId());
        ro.teamnet.ou.domain.neo.Function functionNeoSaved = functionNeoRepository.save(functionNeo);

        return FunctionMapper.toDTO(functionSaved,functionNeoSaved);
    }

//    @Override
//    public Function update(Function function, FunctionDTO functionDTO) {
//
//        function.setCode(functionDTO.getCode());
//        function.setDescription(functionDTO.getDescription());
//        function.setValidFrom(functionDTO.getValidFrom());
//        function.setValidTo(functionDTO.getValidTo());
//        function.setActive(functionDTO.getActive());
//
//
//        //update moduleRights for Role
//        List<ModuleRight> moduleRights = new ArrayList<>();
//        for(ModuleRightDTO mrDTO : functionDTO.getModuleRights()) {
//            if(mrDTO.getId() != null) {
//                moduleRights.add(moduleRightService.findOne(mrDTO.getId()));
//            } else {
//                Module module = moduleRepository.findOne(mrDTO.getModule().getId());
//                Short right = ModuleRightTypeEnum.READ_ACCESS.getRight();
//
//                moduleRights.addAll(moduleRightService.findByModuleAndRight(module, right));
//            }
//        }
//
//        function.setModuleRights(moduleRights);
//
//        return functionRepository.save(function);
//    }

    @Override
    public Set<FunctionDTO> getAllWithModuleRights() {
        Set<Function> functionList = functionRepository.getAllWithModuleRights();
        Set<ro.teamnet.ou.domain.neo.Function> functionListNeo = functionNeoRepository.getAllFunctions();

        Iterator<Function> iteratorJpa = functionList.iterator();
        Iterator<ro.teamnet.ou.domain.neo.Function> iteratorNeo = functionListNeo.iterator();
        Set<FunctionDTO> functionDTOSet = new HashSet<>();
        while (iteratorJpa.hasNext()){
            Function function = iteratorJpa.next();
            while (iteratorNeo.hasNext()){
                ro.teamnet.ou.domain.neo.Function functionNeo = iteratorNeo.next();
                if(function.getId() == functionNeo.getJpaId()){
                    functionDTOSet.add(FunctionMapper.toDTO(function,functionNeo));
                }
            }
        }

        return functionDTOSet;
    }

    @Override
    public void delete(Long id) {
        functionRepository.delete(id);
        functionNeoRepository.delete(id);
    }


    //
//    @Override
//    public Boolean updateRoleById(Long id, FunctionDTO functionDTO) {
//        Function function = this.getOne(id);
//        if (function == null) {
//            return false;
//        }
//        this.update(function, functionDTO);
//        return true;
//    }
}
