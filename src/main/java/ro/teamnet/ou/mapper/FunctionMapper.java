package ro.teamnet.ou.mapper;

import ro.teamnet.bootstrap.domain.ModuleRight;
import ro.teamnet.bootstrap.web.rest.dto.ModuleRightDTO;
import ro.teamnet.ou.domain.jpa.Function;
import ro.teamnet.ou.web.rest.dto.FunctionDTO;

import java.util.HashSet;
import java.util.Set;

public class FunctionMapper {

    public static Function from(FunctionDTO functionDTO) {
        Function function = new Function();

        function.setId(functionDTO.getId());
        function.setCode(functionDTO.getCode());
        function.setDescription(functionDTO.getDescription());
        function.setValidFrom(functionDTO.getValidFrom());
        function.setValidTo(functionDTO.getValidTo());
        function.setActive(functionDTO.getActive());

        Set<ModuleRight> moduleRightSet = new HashSet<>();
        Set<ModuleRightDTO> moduleRights = functionDTO.getModuleRights();
        if(moduleRights != null) {
            for (ModuleRightDTO moduleRightDTO : moduleRights) {
                moduleRightSet.add(ModuleRightMapper.from(moduleRightDTO));
            }
        }
        function.setModuleRights(moduleRightSet);

        return function;
    }

    public static ro.teamnet.ou.domain.neo.Function fromNeo(FunctionDTO functionDTO) {
        ro.teamnet.ou.domain.neo.Function function = new ro.teamnet.ou.domain.neo.Function();

        function.setId(functionDTO.getId());
        function.setCode(functionDTO.getCode());
        function.setAccount(functionDTO.getAccount());
        function.setJpaId(functionDTO.getJpaId());
        function.setOrganizationalUnit(functionDTO.getOrganizationalUnit());

        return function;
    }

    public static FunctionDTO from(Function function, ro.teamnet.ou.domain.neo.Function functionNeo) {
        FunctionDTO functionDTO = new FunctionDTO();

        functionDTO.setId(function.getId());
        functionDTO.setJpaId(functionNeo.getJpaId());
        functionDTO.setOrganizationalUnit(functionNeo.getOrganizationalUnit());
        functionDTO.setAccount(functionNeo.getAccount());
        functionDTO.setActive(function.getActive());
        functionDTO.setCode(function.getCode());
        functionDTO.setValidFrom(function.getValidFrom());
        functionDTO.setValidTo(function.getValidTo());
        functionDTO.setDescription(function.getDescription());

        Set<ModuleRightDTO> moduleRightDTOs = new HashSet<>();
        Set<ModuleRight> moduleRightSet = function.getModuleRights();
        if(moduleRightSet != null) {
            for(ModuleRight moduleRight : moduleRightSet) {
                moduleRightDTOs.add(ModuleRightMapper.from(moduleRight));
            }
        }
        functionDTO.setModuleRights(moduleRightDTOs);

        return functionDTO;
    }
}
