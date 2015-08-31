package ro.teamnet.ou.mapper;

import ro.teamnet.bootstrap.domain.ModuleRight;
import ro.teamnet.bootstrap.web.rest.dto.ModuleRightDTO;
import ro.teamnet.ou.domain.jpa.Function;
import ro.teamnet.ou.web.rest.dto.FunctionDTO;
import ro.teamnet.ou.web.rest.dto.FunctionRelationshipDTO;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class FunctionMapper {

    public static Function toJpa(FunctionDTO functionDTO) {
        if (functionDTO == null) {
            return null;
        }
        return updateJpa(new Function(), functionDTO);
    }

    public static Function updateJpa(Function function, FunctionDTO functionDTO) {
        if (functionDTO == null) {
            return function;
        }
        function.setId(functionDTO.getId());
        function.setCode(functionDTO.getCode());
        function.setDescription(functionDTO.getDescription());
        function.setValidFrom(functionDTO.getValidFrom());
        function.setValidTo(functionDTO.getValidTo());
        function.setActive(functionDTO.getActive());

        Set<ModuleRight> moduleRightSet = new HashSet<>();
        Set<ModuleRightDTO> moduleRights = functionDTO.getModuleRights();
        if (moduleRights != null) {
            for (ModuleRightDTO moduleRightDTO : moduleRights) {
                moduleRightSet.add(ModuleRightMapper.from(moduleRightDTO));
            }
        }
        function.setModuleRights(moduleRightSet);

        return function;
    }

    public static ro.teamnet.ou.domain.neo.Function toNeo(FunctionRelationshipDTO relationshipDTO) {
        if (relationshipDTO == null || relationshipDTO.getAccount() == null || relationshipDTO.getOrganizationalUnit() == null) {
            return null;
        }
        ro.teamnet.ou.domain.neo.Function function = new ro.teamnet.ou.domain.neo.Function();
        function.setId(relationshipDTO.getId());
        function.setJpaId(relationshipDTO.getFunctionId());
        function.setCode(relationshipDTO.getCode());
        function.setAccount(AccountMapper.toNeo(relationshipDTO.getAccount()));
        function.setOrganizationalUnit(OrganizationalUnitMapper.toNeo(relationshipDTO.getOrganizationalUnit()));
        return function;
    }

    public static FunctionDTO toDTO(Function function) {
        return toDTO(function, false);
    }

    public static FunctionDTO toDTO(Function function, boolean lazyFetching) {
        if (function == null) {
            return null;
        }
        FunctionDTO functionDTO = new FunctionDTO();

        functionDTO.setId(function.getId());
        functionDTO.setActive(function.getActive());
        functionDTO.setCode(function.getCode());
        functionDTO.setValidFrom(function.getValidFrom());
        functionDTO.setValidTo(function.getValidTo());
        functionDTO.setDescription(function.getDescription());
        if (!lazyFetching) {
            Set<ModuleRightDTO> moduleRightDTOs = new HashSet<>();
            Set<ModuleRight> moduleRightSet = function.getModuleRights();
            if (moduleRightSet != null) {
                for (ModuleRight moduleRight : moduleRightSet) {
                    moduleRightDTOs.add(ModuleRightMapper.from(moduleRight));
                }
            }
            functionDTO.setModuleRights(moduleRightDTOs);
        }
        return functionDTO;
    }

    public static FunctionDTO toDTO(ro.teamnet.ou.domain.neo.Function function) {
        if (function == null) {
            return null;
        }
        FunctionDTO functionDTO = new FunctionDTO();

        functionDTO.setId(function.getJpaId());
        functionDTO.setCode(function.getCode());
        return functionDTO;
    }

    public static FunctionRelationshipDTO toRelationshipDTO(ro.teamnet.ou.domain.neo.Function functionNeo) {
        if (functionNeo == null) {
            return null;
        }
        FunctionRelationshipDTO relationshipDTO = new FunctionRelationshipDTO();
        relationshipDTO.setId(functionNeo.getId());
        relationshipDTO.setFunctionId(functionNeo.getJpaId());
        relationshipDTO.setOrganizationalUnit(OrganizationalUnitMapper.toDTO(functionNeo.getOrganizationalUnit()));
        relationshipDTO.setAccount(AccountMapper.toDTO(functionNeo.getAccount()));
        return relationshipDTO;
    }

    public static Set<FunctionDTO> toDTO(Collection<Function> functions) {
        Set<FunctionDTO> dtos = new HashSet<>();
        for (Function function : functions) {
            dtos.add(FunctionMapper.toDTO(function));
        }
        return dtos;
    }

}
