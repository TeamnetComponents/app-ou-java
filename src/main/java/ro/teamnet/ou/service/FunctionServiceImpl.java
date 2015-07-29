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
import ro.teamnet.ou.repository.jpa.FunctionRepository;
import ro.teamnet.ou.web.rest.dto.FunctionDTO;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Service class for managing  ModuleRights.
 */
@Service
@Transactional
public class FunctionServiceImpl extends AbstractServiceImpl<Function,Long> implements FunctionService {


    private final FunctionRepository functionRepository;

    private final ModuleRightService moduleRightService;

    private final ModuleRepository moduleRepository;

    @Inject
    public FunctionServiceImpl(FunctionRepository functionRepository, ModuleRightService moduleRightService, ModuleRepository moduleRepository) {
        super(functionRepository);
        this.functionRepository = functionRepository;
        this.moduleRightService=moduleRightService;
        this.moduleRepository=moduleRepository;
    }


    @Override
    public Function getOne(Long id) {
        return functionRepository.getOne(id);
    }


    public Function getOneById(Long id) {
        return functionRepository.getOneById(id);
    }


    @Override
    public Function update(Function function) {
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

        return functionRepository.save(function);
    }

    @Override
    public Function update(Function function, FunctionDTO functionDTO) {

        function.setCode(functionDTO.getCode());
        function.setDescription(functionDTO.getDescription());
        function.setValidFrom(functionDTO.getValidFrom());
        function.setValidTo(functionDTO.getValidTo());
        function.setActive(functionDTO.getActive());


        //update moduleRights for Role
        List<ModuleRight> moduleRights = new ArrayList<>();
        for(ModuleRightDTO mrDTO : functionDTO.getModuleRights()) {
            if(mrDTO.getId() != null) {
                moduleRights.add(moduleRightService.findOne(mrDTO.getId()));
            } else {
                Module module = moduleRepository.findOne(mrDTO.getModule().getId());
                Short right = ModuleRightTypeEnum.READ_ACCESS.getRight();

                moduleRights.addAll(moduleRightService.findByModuleAndRight(module, right));
            }
        }

        function.setModuleRights(moduleRights);

        return functionRepository.save(function);
    }

    @Override
    public Set<Function> getAllWithModuleRights() {
        return functionRepository.getAllWithModuleRights();
    }

    @Override
    public Boolean updateRoleById(Long id, FunctionDTO functionDTO) {
        Function function = this.getOne(id);
        if (function == null) {
            return false;
        }
        this.update(function, functionDTO);
        return true;
    }
}
