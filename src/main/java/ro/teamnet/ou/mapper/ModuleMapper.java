package ro.teamnet.ou.mapper;

import ro.teamnet.bootstrap.domain.Module;
import ro.teamnet.bootstrap.domain.ModuleRight;
import ro.teamnet.bootstrap.web.rest.dto.ModuleDTO;
import ro.teamnet.bootstrap.web.rest.dto.ModuleRightDTO;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ModuleMapper {

    public static Module from(ModuleDTO moduleDTO) {
        Module module = new Module();

        module.setId(moduleDTO.getId());
        module.setVersion(moduleDTO.getVersion());
        module.setDescription(moduleDTO.getDescription());
        module.setCode(moduleDTO.getCode());
        module.setParentModule(module.getParentModule());
        module.setType(moduleDTO.getType());
        Set<ModuleRight> moduleRights = new HashSet<>();
        Set<ModuleRightDTO> moduleRightDTOSet = moduleDTO.getModuleRights();
        if(moduleRightDTOSet != null) {
            for(ModuleRightDTO moduleRightDTO : moduleRightDTOSet) {
                moduleRights.add(ModuleRightMapper.from(moduleRightDTO));
            }
        }
        module.setModuleRights(moduleRights);

        return module;
    }

    public static ModuleDTO from(Module module) {
        ModuleDTO moduleDTO = new ModuleDTO();

        moduleDTO.setId(module.getId());
        moduleDTO.setType(module.getType());
        moduleDTO.setParentModule(module.getParentModule());
        moduleDTO.setCode(module.getCode());
        moduleDTO.setDescription(module.getDescription());
        moduleDTO.setVersion(module.getVersion());

        Set<ModuleRightDTO> moduleRightDTOs = new HashSet<>();
        Collection<ModuleRight> moduleRightSet = module.getModuleRights();
        if(moduleRightSet != null) {
            for(ModuleRight moduleRight : moduleRightSet) {
                moduleRightDTOs.add(ModuleRightMapper.from(moduleRight));
            }
        }
        moduleDTO.setModuleRights(moduleRightDTOs);

        return moduleDTO;
    }
}
