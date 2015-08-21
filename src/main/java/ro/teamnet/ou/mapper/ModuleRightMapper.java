package ro.teamnet.ou.mapper;

import ro.teamnet.bootstrap.domain.ModuleRight;
import ro.teamnet.bootstrap.web.rest.dto.ModuleRightDTO;

public class ModuleRightMapper {

    public static ModuleRight from(ModuleRightDTO moduleRightDTO) {
        ModuleRight moduleRight = new ModuleRight();

        moduleRight.setId(moduleRightDTO.getId());
        moduleRight.setRight(moduleRightDTO.getRight());
        moduleRight.setModule(ModuleMapper.from(moduleRightDTO.getModule()));
        moduleRight.setVersion(moduleRightDTO.getVersion());

        return moduleRight;
    }

    public static ModuleRightDTO from(ModuleRight moduleRight) {
        ModuleRightDTO moduleRightDTO = new ModuleRightDTO();

        moduleRightDTO.setId(moduleRight.getId());
        moduleRightDTO.setVersion(moduleRight.getVersion());
        moduleRightDTO.setRight(moduleRight.getRight());
        moduleRightDTO.setModule(ModuleMapper.lazyFrom(moduleRight.getModule()));

        return moduleRightDTO;
    }
}
