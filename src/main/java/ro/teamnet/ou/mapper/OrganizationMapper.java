package ro.teamnet.ou.mapper;

import ro.teamnet.ou.domain.jpa.Organization;
import ro.teamnet.ou.domain.jpa.Perspective;
import ro.teamnet.ou.domain.neo.OrganizationalUnit;
import ro.teamnet.ou.web.rest.dto.OrganizationDTO;
import ro.teamnet.ou.web.rest.dto.OrganizationalUnitDTO;
import ro.teamnet.ou.web.rest.dto.PerspectiveDTO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OrganizationMapper {

    public static Organization toJPA(OrganizationDTO organizationDTO) {
        Organization organization = new Organization();

        organization.setId(organizationDTO.getId());
        organization.setCode(organizationDTO.getCode());
        organization.setDescription(organizationDTO.getDescription());
        organization.setValidFrom(organizationDTO.getValidFrom());
        organization.setValidTo(organizationDTO.getValidTo());
        organization.setActive(organizationDTO.getActive());

        Set<Perspective> perspectiveSet = new HashSet<>();
        Set<PerspectiveDTO> perspectiveDTOSet = organizationDTO.getPerspectives();
        if (perspectiveDTOSet != null) {
            for (PerspectiveDTO perspectiveDTO : perspectiveDTOSet) {
                perspectiveSet.add(PerspectiveMapper.from(perspectiveDTO));
            }
        }
        organization.setPerspectives(perspectiveSet);

        return organization;
    }

    public static ro.teamnet.ou.domain.neo.Organization toNeo(OrganizationDTO organizationDTO) {
        ro.teamnet.ou.domain.neo.Organization organization = new ro.teamnet.ou.domain.neo.Organization();

        organization.setId(organizationDTO.getId());
        organization.setJpaId(organizationDTO.getJpaId());
        organization.setCode(organizationDTO.getCode());

        Set<ro.teamnet.ou.domain.neo.Perspective> perspectiveSet = new HashSet<>();
        Set<PerspectiveDTO> perspectiveDTOs = organizationDTO.getPerspectives();
        if (perspectiveDTOs != null) {
            for (PerspectiveDTO perspectiveDTO : perspectiveDTOs) {
                perspectiveSet.add(PerspectiveMapper.fromNeo(perspectiveDTO));
            }
        }
        organization.setPerspectives(perspectiveSet);

        Set<OrganizationalUnit> organizationalUnitSet = new HashSet<>();
        Set<OrganizationalUnitDTO> organizationalUnitDTOs = organizationDTO.getRoots();
        if (organizationalUnitDTOs != null) {
            for (OrganizationalUnitDTO organizationalUnitDTO : organizationalUnitDTOs) {
                organizationalUnitSet.add(OrganizationalUnitMapper.fromNeo(organizationalUnitDTO));
            }
        }
        organization.setRoots(organizationalUnitSet);

        return organization;
    }

    public static OrganizationDTO toDTO(Organization organization, ro.teamnet.ou.domain.neo.Organization organizationNeo) {
        OrganizationDTO organizationDTO = new OrganizationDTO();

        organizationDTO.setId(organization.getId());
        organizationDTO.setJpaId(organizationNeo.getJpaId());
        organizationDTO.setCode(organization.getCode());
        organizationDTO.setDescription(organization.getDescription());
        organizationDTO.setValidFrom(organization.getValidFrom());
        organizationDTO.setValidTo(organization.getValidTo());
        organizationDTO.setActive(organization.getActive());

        Set<PerspectiveDTO> perspectiveDTOs = new HashSet<>();
        List<Perspective> perspectiveList = new ArrayList<>(organization.getPerspectives());
        List<ro.teamnet.ou.domain.neo.Perspective> perspectiveNeoList = new ArrayList<>(organizationNeo.getPerspectives());
        if (perspectiveList != null) {
            for (int i = 0; i < perspectiveList.size(); i++) {
                perspectiveDTOs.add(PerspectiveMapper.from(perspectiveList.get(i), perspectiveNeoList.get(i)));
            }
        }
        organizationDTO.setPerspectives(perspectiveDTOs);

        Set<OrganizationalUnitDTO> organizationalUnitDTOs = new HashSet<>();
        Set<OrganizationalUnit> organizationalUnitSet = organizationNeo.getRoots();
        if (organizationalUnitSet != null) {
            for (OrganizationalUnit organizationalUnit : organizationalUnitSet) {
                organizationalUnitDTOs.add(OrganizationalUnitMapper.from(null, organizationalUnit));
            }
        }
        organizationDTO.setRoots(organizationalUnitDTOs);

        return organizationDTO;
    }
}
