package ro.teamnet.ou.mapper;

import ro.teamnet.ou.domain.jpa.Perspective;
import ro.teamnet.ou.web.rest.dto.PerspectiveDTO;

public class PerspectiveMapper {
    /**
     * Converts the DTO for Perspective into a JPA entity.
     *
     * @param perspectiveDTO the perspective DTO
     * @param lazyFetching   if true, the reference to the organization will not be added to the entity
     * @return the DTO
     */
    public static Perspective toJPA(PerspectiveDTO perspectiveDTO, boolean lazyFetching) {
        Perspective perspective = new Perspective();

        perspective.setId(perspectiveDTO.getId());
        perspective.setCode(perspectiveDTO.getCode());
        perspective.setDescription(perspectiveDTO.getDescription());
        perspective.setOuTreeRoot(OrganizationalUnitMapper.toJPA(perspectiveDTO.getOuTreeRoot()));
        if (!lazyFetching && perspectiveDTO.getOrganization() != null) {
            perspective.setOrganization(OrganizationMapper.toJPA(perspectiveDTO.getOrganization(), true));
        }
        return perspective;
    }

    /**
     * Converts the DTO for Perspective into a JPA entity. A reference to the organization will be added to the entity.
     *
     * @param perspectiveDTO the perspective DTO
     * @return the DTO
     */
    public static Perspective toJPA(PerspectiveDTO perspectiveDTO) {
        return toJPA(perspectiveDTO, false);
    }

    /**
     * Converts the DTO for Perspective into a Neo4J entity.
     *
     * @param perspectiveDTO the DTO
     * @return the Neo4J entity
     */
    public static ro.teamnet.ou.domain.neo.Perspective toNeo(PerspectiveDTO perspectiveDTO) {
        if (perspectiveDTO.getOrganization() == null || perspectiveDTO.getOuTreeRoot() == null) {
            return null;
        }
        ro.teamnet.ou.domain.neo.Perspective perspective = new ro.teamnet.ou.domain.neo.Perspective();

        perspective.setJpaId(perspectiveDTO.getId());
        perspective.setCode(perspectiveDTO.getCode());
        perspective.setOrganization(OrganizationMapper.toNeo(perspectiveDTO.getOrganization()));
        perspective.setOrganizationalUnit(OrganizationalUnitMapper.toNeo(perspectiveDTO.getOuTreeRoot()));
        return perspective;
    }

    /**
     * Converts the JPA entity for Perspective into a DTO.
     *
     * @param perspective  the perspective
     * @param lazyFetching if true, the reference to the organization will not be added to the DTO
     * @return the DTO
     */
    public static PerspectiveDTO toDTO(Perspective perspective, boolean lazyFetching) {
        PerspectiveDTO perspectiveDTO = new PerspectiveDTO();

        perspectiveDTO.setId(perspective.getId());
        perspectiveDTO.setCode(perspective.getCode());
        perspectiveDTO.setDescription(perspective.getDescription());
        perspectiveDTO.setOuTreeRoot(OrganizationalUnitMapper.toDTO(perspective.getOuTreeRoot(), true));
        if (!lazyFetching) {
            perspectiveDTO.setOrganization(OrganizationMapper.toDTO(perspective.getOrganization(), true));
        }
        return perspectiveDTO;
    }

    /**
     * Converts the JPA entity for Perspective into a DTO. A reference to the organization will be added to the DTO.
     *
     * @param perspective the perspective
     * @return the DTO
     */
    public static PerspectiveDTO toDTO(Perspective perspective) {
        return toDTO(perspective, false);
    }
}
