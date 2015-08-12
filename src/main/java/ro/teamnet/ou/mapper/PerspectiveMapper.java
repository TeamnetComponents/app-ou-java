package ro.teamnet.ou.mapper;

import ro.teamnet.ou.domain.jpa.OrganizationalUnit;
import ro.teamnet.ou.domain.jpa.Perspective;
import ro.teamnet.ou.web.rest.dto.OrganizationalUnitDTO;
import ro.teamnet.ou.web.rest.dto.PerspectiveDTO;

import java.util.HashSet;
import java.util.Set;

public class PerspectiveMapper {

    public static Perspective from(PerspectiveDTO perspectiveDTO) {
        Perspective perspective = new Perspective();

        perspective.setId(perspectiveDTO.getId());
        perspective.setCode(perspectiveDTO.getCode());
        perspective.setDescription(perspectiveDTO.getDescription());
        perspective.setOrganization(OrganizationMapper.from(perspectiveDTO.getOrganization()));
        Set<OrganizationalUnit> organizationalUnitSet = new HashSet<>();
        if(organizationalUnitSet != null) {
            for (OrganizationalUnitDTO organizationalUnitDTO : perspectiveDTO.getOrganizationalUnitSet()) {
                organizationalUnitSet.add(OrganizationalUnitMapper.from(organizationalUnitDTO));
            }
        }
        perspective.setOrganizationalUnits(organizationalUnitSet);

        return perspective;
    }

    public static ro.teamnet.ou.domain.neo.Perspective fromNeo(PerspectiveDTO perspectiveDTO) {
        ro.teamnet.ou.domain.neo.Perspective perspective = new ro.teamnet.ou.domain.neo.Perspective();

        perspective.setId(perspectiveDTO.getId());
        perspective.setJpaId(perspectiveDTO.getJpaId());
        perspective.setCode(perspectiveDTO.getCode());
        perspective.setOrganization(OrganizationMapper.fromNeo(perspectiveDTO.getOrganization()));
        perspective.setOrganizationalUnit(OrganizationalUnitMapper.fromNeo(perspectiveDTO.getOrganizationalUnit()));

        return perspective;
    }

    public static PerspectiveDTO from(Perspective perspective, ro.teamnet.ou.domain.neo.Perspective perspectiveNeo) {
        PerspectiveDTO perspectiveDTO = new PerspectiveDTO();

        perspectiveDTO.setId(perspective.getId());
        perspectiveDTO.setCode(perspective.getCode());
        perspectiveDTO.setDescription(perspective.getDescription());
        perspectiveDTO.setJpaId(perspectiveNeo.getJpaId());
        perspectiveDTO.setOrganization(OrganizationMapper.from(perspective.getOrganization(), perspectiveNeo.getOrganization()));
        perspectiveDTO.setOrganizationalUnit(OrganizationalUnitMapper.from(null, perspectiveNeo.getOrganizationalUnit()));

        Set<OrganizationalUnitDTO> organizationalUnitDTOs = new HashSet<>();
        if(organizationalUnitDTOs != null) {
            for (OrganizationalUnit organizationalUnit : perspective.getOrganizationalUnits()) {
                organizationalUnitDTOs.add(OrganizationalUnitMapper.from(organizationalUnit, null));
            }
        }
        perspectiveDTO.setOrganizationalUnitSet(organizationalUnitDTOs);

        return perspectiveDTO;
    }
}
