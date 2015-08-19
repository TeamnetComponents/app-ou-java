package ro.teamnet.ou.mapper;

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
        perspective.setOrganization(OrganizationMapper.toJPA(perspectiveDTO.getOrganization()));
        Set<OrganizationalUnit> organizationalUnitSet = new HashSet<>();
        if(organizationalUnitSet != null) {
            for (OrganizationalUnitDTO organizationalUnitDTO : perspectiveDTO.getOrganizationalUnitSet()) {
                organizationalUnitSet.add(OrganizationalUnitMapper.toJPA(organizationalUnitDTO));
            }
        }
        perspective.setOrganizationalUnits(organizationalUnitSet);

        return perspective;
    }

    public static ro.teamnet.ou.domain.neo.Perspective toNeo(PerspectiveDTO perspectiveDTO) {
        ro.teamnet.ou.domain.neo.Perspective perspective = new ro.teamnet.ou.domain.neo.Perspective();

        perspective.setJpaId(perspectiveDTO.getId());
        perspective.setCode(perspectiveDTO.getCode());
        perspective.setOrganization(OrganizationMapper.toNeo(perspectiveDTO.getOrganization()));
        perspective.setOrganizationalUnit(OrganizationalUnitMapper.toNeo(perspectiveDTO.getOrganizationalUnit()));

        return perspective;
    }

    public static PerspectiveDTO toDTO(Perspective perspective, ro.teamnet.ou.domain.neo.Perspective perspectiveNeo) {
        PerspectiveDTO perspectiveDTO = new PerspectiveDTO();

        perspectiveDTO.setId(perspectiveNeo.getJpaId());
        perspectiveDTO.setCode(perspective.getCode());
        perspectiveDTO.setDescription(perspective.getDescription());
        perspectiveDTO.setOrganization(OrganizationMapper.toDTO(perspective.getOrganization(), perspectiveNeo.getOrganization()));
        perspectiveDTO.setOrganizationalUnit(OrganizationalUnitMapper.toDTO(null, perspectiveNeo.getOrganizationalUnit()));

        Set<OrganizationalUnitDTO> organizationalUnitDTOs = new HashSet<>();
        if(organizationalUnitDTOs != null) {
            for (OrganizationalUnit organizationalUnit : perspective.getOrganizationalUnits()) {
                organizationalUnitDTOs.add(OrganizationalUnitMapper.toDTO(organizationalUnit, null));
            }
        }
        perspectiveDTO.setOrganizationalUnitSet(organizationalUnitDTOs);

        return perspectiveDTO;
    }
}
