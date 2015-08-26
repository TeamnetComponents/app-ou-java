package ro.teamnet.ou.mapper;

import ro.teamnet.ou.domain.jpa.Organization;
import ro.teamnet.ou.domain.jpa.OrganizationalUnit;
import ro.teamnet.ou.domain.jpa.Perspective;
import ro.teamnet.ou.web.rest.dto.OrganizationalUnitDTO;
import ro.teamnet.ou.web.rest.dto.PerspectiveDTO;

import java.util.HashSet;
import java.util.Set;

public class PerspectiveMapper {

    public static Perspective toJPA(PerspectiveDTO perspectiveDTO) {
        Perspective perspective = new Perspective();

        perspective.setId(perspectiveDTO.getId());
        perspective.setCode(perspectiveDTO.getCode());
        perspective.setDescription(perspectiveDTO.getDescription());
        perspective.setOrganization(OrganizationMapper.toJPAlazy(perspectiveDTO.getOrganizationDto()));
        Set<OrganizationalUnit> organizationalUnitSet = new HashSet<>();
        if(perspectiveDTO.getOrganizationalUnitSet() != null) {
            for (OrganizationalUnitDTO organizationalUnitDTO : perspectiveDTO.getOrganizationalUnitSet()) {
                organizationalUnitSet.add(OrganizationalUnitMapper.toJPA(organizationalUnitDTO));
            }
        }
        perspective.setOrganizationalUnits(organizationalUnitSet);

        return perspective;
    }

    public static Perspective toJPAlazy(PerspectiveDTO perspectiveDTO){
        Perspective perspective = new Perspective();
        perspective.setId(perspectiveDTO.getId());
        perspective.setCode(perspectiveDTO.getCode());
        perspective.setDescription(perspectiveDTO.getDescription());
        return perspective;
    }

    public static ro.teamnet.ou.domain.neo.Perspective toNeo(PerspectiveDTO perspectiveDTO) {
        ro.teamnet.ou.domain.neo.Perspective perspective = new ro.teamnet.ou.domain.neo.Perspective();

        perspective.setJpaId(perspectiveDTO.getId());
        perspective.setCode(perspectiveDTO.getCode());
        if(perspectiveDTO.getOrganizationDto()!=null) {
            perspective.setOrganization(OrganizationMapper.toNeo(perspectiveDTO.getOrganizationDto()));
        }
        if(perspectiveDTO.getOrganizationalUnit()!=null) {
            perspective.setOrganizationalUnit(OrganizationalUnitMapper.toNeo(perspectiveDTO.getOrganizationalUnit()));
        }
        return perspective;
    }

    public static PerspectiveDTO toDTO(Perspective perspective, ro.teamnet.ou.domain.neo.Perspective perspectiveNeo) {
        PerspectiveDTO perspectiveDTO = new PerspectiveDTO();

        perspectiveDTO.setId(perspectiveNeo.getJpaId());
        perspectiveDTO.setCode(perspective.getCode());
        perspectiveDTO.setDescription(perspective.getDescription());
        perspectiveDTO.setOrganizationDto(OrganizationMapper.toDTO(perspective.getOrganization(), perspectiveNeo.getOrganization()));
        perspectiveDTO.setOrganizationalUnit(OrganizationalUnitMapper.toDTO(perspectiveNeo.getOrganizationalUnit()));

        Set<OrganizationalUnitDTO> organizationalUnitDTOs = new HashSet<>();
        if(organizationalUnitDTOs != null) {
            for (OrganizationalUnit organizationalUnit : perspective.getOrganizationalUnits()) {
                organizationalUnitDTOs.add(OrganizationalUnitMapper.toDTO(organizationalUnit));
            }
        }
        perspectiveDTO.setOrganizationalUnitSet(organizationalUnitDTOs);

        return perspectiveDTO;
    }

    public static PerspectiveDTO toDTOLazy(Perspective perspective, ro.teamnet.ou.domain.neo.Perspective perspectiveNeo) {
        PerspectiveDTO perspectiveDTO = new PerspectiveDTO();

        perspectiveDTO.setId(perspectiveNeo.getJpaId());
        perspectiveDTO.setCode(perspective.getCode());
        perspectiveDTO.setDescription(perspective.getDescription());

        return perspectiveDTO;
    }

    public static PerspectiveDTO toDTOLazy(Perspective perspective) {
        PerspectiveDTO perspectiveDTO = new PerspectiveDTO();

        perspectiveDTO.setId(perspective.getId());
        perspectiveDTO.setCode(perspective.getCode());
        perspectiveDTO.setDescription(perspective.getDescription());

        return perspectiveDTO;
    }
    public static PerspectiveDTO toDTO(Perspective perspective) {
        PerspectiveDTO perspectiveDTO = new PerspectiveDTO();
        perspectiveDTO.setId(perspective.getId());
        perspectiveDTO.setCode(perspective.getCode());
        perspectiveDTO.setDescription(perspective.getDescription());
        perspectiveDTO.setOrganizationDto(OrganizationMapper.toDTO(perspective.getOrganization()));
//        Set<OrganizationalUnitDTO> organizationalUnitDTOs = new HashSet<>();
//        if(perspective.getOrganizationalUnits() != null) {
//            for (OrganizationalUnit organizationalUnit : perspective.getOrganizationalUnits()) {
//                organizationalUnitDTOs.add(OrganizationalUnitMapper.toDTO(organizationalUnit));
//            }
//        }
//        perspectiveDTO.setOrganizationalUnitSet(organizationalUnitDTOs);

        return perspectiveDTO;
    }
    public static PerspectiveDTO toDTO( ro.teamnet.ou.domain.neo.Perspective perspectiveNeo) {
        PerspectiveDTO perspectiveDTO = new PerspectiveDTO();

        perspectiveDTO.setId(perspectiveNeo.getJpaId());
        perspectiveDTO.setOrganizationalUnit(OrganizationalUnitMapper.toDTO(perspectiveNeo.getOrganizationalUnit()));

        return perspectiveDTO;
    }
}
