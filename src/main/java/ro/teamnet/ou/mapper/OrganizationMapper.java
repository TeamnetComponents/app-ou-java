package ro.teamnet.ou.mapper;

import ro.teamnet.ou.domain.jpa.Organization;
import ro.teamnet.ou.domain.jpa.Perspective;
import ro.teamnet.ou.web.rest.dto.OrganizationDTO;
import ro.teamnet.ou.web.rest.dto.PerspectiveDTO;

import java.util.HashSet;
import java.util.Set;

public class OrganizationMapper {
    /**
     * Converts the DTO for Organization into a JPA entity.
     *
     * @param organizationDTO the organization DTO
     * @param lazyFetching    if true, the set of perspectives will not be added to the entity
     * @return the JPA entity
     */
    public static Organization toJPA(OrganizationDTO organizationDTO, boolean lazyFetching) {
        Organization organization = new Organization();

        organization.setId(organizationDTO.getId());
        organization.setCode(organizationDTO.getCode());
        organization.setDescription(organizationDTO.getDescription());
        organization.setValidFrom(organizationDTO.getValidFrom());
        organization.setValidTo(organizationDTO.getValidTo());
        organization.setActive(organizationDTO.getActive());

        if (!lazyFetching) {
            Set<Perspective> perspectiveSet = new HashSet<>();
            Set<PerspectiveDTO> perspectiveDTOSet = organizationDTO.getPerspectives();

            if (perspectiveDTOSet != null) {
                for (PerspectiveDTO perspectiveDTO : perspectiveDTOSet) {
                    perspectiveSet.add(PerspectiveMapper.toJPAlazy(perspectiveDTO));
                }
            }
            organization.setPerspectives(perspectiveSet);
        }
        return organization;
    }

    /**
     * Converts the DTO for Organization into a JPA entity, with eager fetching of the perspective set.
     *
     * @param organizationDTO the organization DTO
     * @return the JPA entity
     */
    public static Organization toJPA(OrganizationDTO organizationDTO) {
        return toJPA(organizationDTO, false);
    }

    /**
     * Converts the DTO for Organization into a Neo4J entity.
     *
     * @param organizationDTO the organization DTO
     * @return the Neo4J entity
     */
    public static ro.teamnet.ou.domain.neo.Organization toNeo(OrganizationDTO organizationDTO) {
        ro.teamnet.ou.domain.neo.Organization organization = new ro.teamnet.ou.domain.neo.Organization();
        organization.setJpaId(organizationDTO.getId());
        organization.setCode(organizationDTO.getCode());
        return organization;
    }

    /**
     * Converts the JPA entity for Organization into a DTO.
     *
     * @param organization the organization
     * @param lazyFetching if true, the set of perspectives will not be added to the DTO
     * @return the DTO
     */
    public static OrganizationDTO toDTO(Organization organization, boolean lazyFetching) {
        OrganizationDTO organizationDTO = new OrganizationDTO();
        organizationDTO.setId(organization.getId());
        organizationDTO.setCode(organization.getCode());
        organizationDTO.setDescription(organization.getDescription());
        organizationDTO.setValidFrom(organization.getValidFrom());
        organizationDTO.setValidTo(organization.getValidTo());
        organizationDTO.setActive(organization.getActive());

        if (!lazyFetching) {
            Set<PerspectiveDTO> perspectiveDTOs = new HashSet<>();
            if (organization.getPerspectives() != null) {
                for (Perspective perspective : organization.getPerspectives()) {
                    perspectiveDTOs.add(PerspectiveMapper.toDTO(perspective, true));
                }
            }
            organizationDTO.setPerspectives(perspectiveDTOs);
        }
        return organizationDTO;
    }

    /**
     * Converts the JPA entity for Organization into a DTO, with eager fetching of the perspective set.
     *
     * @param organization the organization
     * @return the DTO
     */
    public static OrganizationDTO toDTO(Organization organization) {
        return toDTO(organization, false);
    }
}
