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
                perspectiveSet.add(PerspectiveMapper.toJPAlazy(perspectiveDTO));
            }
        }
        organization.setPerspectives(perspectiveSet);

        return organization;
    }

    public static Organization toJPAlazy(OrganizationDTO organizationDTO) {
        Organization organization = new Organization();

        organization.setId(organizationDTO.getId());
        organization.setCode(organizationDTO.getCode());
        organization.setDescription(organizationDTO.getDescription());
        organization.setValidFrom(organizationDTO.getValidFrom());
        organization.setValidTo(organizationDTO.getValidTo());
        organization.setActive(organizationDTO.getActive());

        return organization;
    }

    public static ro.teamnet.ou.domain.neo.Organization toNeo(OrganizationDTO organizationDTO) {
        ro.teamnet.ou.domain.neo.Organization organization = new ro.teamnet.ou.domain.neo.Organization();

        organization.setJpaId(organizationDTO.getId());
        organization.setCode(organizationDTO.getCode());

        Set<ro.teamnet.ou.domain.neo.Perspective> perspectiveSet = new HashSet<>();
        Set<PerspectiveDTO> perspectiveDTOs = organizationDTO.getPerspectives();
        if (perspectiveDTOs != null) {
            for (PerspectiveDTO perspectiveDTO : perspectiveDTOs) {
                perspectiveSet.add(PerspectiveMapper.toNeo(perspectiveDTO));
            }
        }
        organization.setPerspectives(perspectiveSet);

        Set<OrganizationalUnit> organizationalUnitSet = new HashSet<>();
        Set<OrganizationalUnitDTO> organizationalUnitDTOs = organizationDTO.getRoots();
        if (organizationalUnitDTOs != null) {
            for (OrganizationalUnitDTO organizationalUnitDTO : organizationalUnitDTOs) {
                organizationalUnitSet.add(OrganizationalUnitMapper.toNeo(organizationalUnitDTO));
            }
        }
        organization.setRoots(organizationalUnitSet);

        return organization;
    }

    public static OrganizationDTO toDTO(Organization organization, ro.teamnet.ou.domain.neo.Organization organizationNeo) {
        OrganizationDTO organizationDTO = new OrganizationDTO();

        organizationDTO.setId(organizationNeo.getJpaId());
        organizationDTO.setCode(organization.getCode());
        organizationDTO.setDescription(organization.getDescription());
        organizationDTO.setValidFrom(organization.getValidFrom());
        organizationDTO.setValidTo(organization.getValidTo());
        organizationDTO.setActive(organization.getActive());

        Set<PerspectiveDTO> perspectiveDTOs = new HashSet<>();
        Set<Perspective> perspectiveSet = organization.getPerspectives();
        Set<ro.teamnet.ou.domain.neo.Perspective> perspectiveNeoSet = organizationNeo.getPerspectives();
        if (perspectiveSet != null && perspectiveNeoSet != null) {
            for (Perspective perspective : perspectiveSet) {
                for(ro.teamnet.ou.domain.neo.Perspective perspectiveNeo : perspectiveNeoSet){
                    if(perspectiveNeo.getJpaId() == perspective.getId()){
                        perspectiveDTOs.add(PerspectiveMapper.toDTOLazy(perspective, perspectiveNeo));
                    }
                }
            }
        }
        organizationDTO.setPerspectives(perspectiveDTOs);

        Set<OrganizationalUnitDTO> organizationalUnitDTOs = new HashSet<>();
        Set<OrganizationalUnit> organizationalUnitSet = organizationNeo.getRoots();
        if (organizationalUnitSet != null) {
            for (OrganizationalUnit organizationalUnit : organizationalUnitSet) {
                organizationalUnitDTOs.add(OrganizationalUnitMapper.toDTO(organizationalUnit));
            }
        }
        organizationDTO.setRoots(organizationalUnitDTOs);

        return organizationDTO;
    }

    public static OrganizationDTO toDTO(Organization organization) {
        OrganizationDTO organizationDTO = new OrganizationDTO();
        organizationDTO.setId(organization.getId());
        organizationDTO.setCode(organization.getCode());
        organizationDTO.setDescription(organization.getDescription());
        organizationDTO.setValidFrom(organization.getValidFrom());
        organizationDTO.setValidTo(organization.getValidTo());
        organizationDTO.setActive(organization.getActive());

        Set<PerspectiveDTO> perspectiveDTOs = new HashSet<>();
        if (organization.getPerspectives() != null) {
            for (Perspective perspective : organization.getPerspectives()) {
                perspectiveDTOs.add(PerspectiveMapper.toDTOLazy(perspective));
            }
        }
        organizationDTO.setPerspectives(perspectiveDTOs);

        return organizationDTO;
    }

    public static OrganizationDTO toDTOLazy(Organization organization) {
        OrganizationDTO organizationDTO = new OrganizationDTO();
        organizationDTO.setId(organization.getId());
        organizationDTO.setCode(organization.getCode());
        organizationDTO.setDescription(organization.getDescription());
        organizationDTO.setValidFrom(organization.getValidFrom());
        organizationDTO.setValidTo(organization.getValidTo());
        organizationDTO.setActive(organization.getActive());

        return organizationDTO;
    }
}
