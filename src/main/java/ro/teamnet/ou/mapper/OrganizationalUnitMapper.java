package ro.teamnet.ou.mapper;

import ro.teamnet.ou.domain.jpa.OrganizationalUnit;
import ro.teamnet.ou.web.rest.dto.OrganizationalUnitDTO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OrganizationalUnitMapper {
    /**
     * Converts the DTO for an organizational unit into a JPA entity.
     *
     * @param organizationalUnitDTO the organizational unit DTO
     * @param lazyFetching          if true, the references to perspective and children wil not be added to the entity
     * @return the JPA entity
     */
    public static OrganizationalUnit toJPA(OrganizationalUnitDTO organizationalUnitDTO, boolean lazyFetching) {
        if (organizationalUnitDTO == null) {
            return null;
        }
        OrganizationalUnit organizationalUnit = new OrganizationalUnit();

        organizationalUnit.setId(organizationalUnitDTO.getId());
        organizationalUnit.setCode(organizationalUnitDTO.getCode());
        organizationalUnit.setDescription(organizationalUnitDTO.getDescription());
        organizationalUnit.setValidFrom(organizationalUnitDTO.getValidFrom());
        organizationalUnit.setValidTo(organizationalUnitDTO.getValidTo());
        organizationalUnit.setActive(organizationalUnitDTO.getActive());
        if (organizationalUnitDTO.getParent() != null) {
            organizationalUnit.setParent(OrganizationalUnitMapper.toJPA(organizationalUnitDTO.getParent()));
        }
        if (!lazyFetching) {
            if (organizationalUnitDTO.getPerspective() != null) {
                organizationalUnit.setPerspective(PerspectiveMapper.toJPA(organizationalUnitDTO.getPerspective(), true));
            }
            Set<OrganizationalUnit> organizationalUnitSet = new HashSet<>();
            if (organizationalUnitDTO.getChildren() != null) {
                for (OrganizationalUnitDTO item : organizationalUnitDTO.getChildren()) {
                    OrganizationalUnit organizationalUnitItem = OrganizationalUnitMapper.toJPA(item);
                    organizationalUnitSet.add(organizationalUnitItem);
                }
            }
            organizationalUnit.setChildren(organizationalUnitSet);
        }
        return organizationalUnit;
    }

    /**
     * Converts the DTO for an organizational unit into a JPA entity. The references to the perspective and children
     * are included in the entity.
     *
     * @param organizationalUnitDTO the organizational unit DTO
     * @return the JPA entity
     */
    public static OrganizationalUnit toJPA(OrganizationalUnitDTO organizationalUnitDTO) {
        return toJPA(organizationalUnitDTO, false);
    }

    /**
     * Converts the DTO for an organizational unit into a Neo4J entity.
     *
     * @param organizationalUnitDTO the organizational unit DTO
     * @return the Neo4J entity
     */
    public static ro.teamnet.ou.domain.neo.OrganizationalUnit toNeo(OrganizationalUnitDTO organizationalUnitDTO) {
        ro.teamnet.ou.domain.neo.OrganizationalUnit organizationalUnit = new ro.teamnet.ou.domain.neo.OrganizationalUnit();
        organizationalUnit.setJpaId(organizationalUnitDTO.getId());
        organizationalUnit.setCode(organizationalUnitDTO.getCode());
        return organizationalUnit;
    }

    /**
     * Converts the JPA entity for an organizational unit into a DTO.
     *
     * @param organizationalUnit the organizational unit JPA entity
     * @param lazyFetching       if true, the references to perspective and children wil not be added to the DTO
     * @return the DTO
     */
    public static OrganizationalUnitDTO toDTO(OrganizationalUnit organizationalUnit, boolean lazyFetching) {
        if (organizationalUnit == null) {
            return null;
        }
        OrganizationalUnitDTO organizationalUnitDTO = new OrganizationalUnitDTO();

        organizationalUnitDTO.setId(organizationalUnit.getId());
        organizationalUnitDTO.setCode(organizationalUnit.getCode());
        organizationalUnitDTO.setDescription(organizationalUnit.getDescription());
        organizationalUnitDTO.setValidFrom(organizationalUnit.getValidFrom());
        organizationalUnitDTO.setValidTo(organizationalUnit.getValidTo());
        organizationalUnitDTO.setActive(organizationalUnit.getActive());

        if (organizationalUnit.getParent() != null) {
            organizationalUnitDTO.setParent(OrganizationalUnitMapper.toDTO(organizationalUnit.getParent(), true));
        }
        if (!lazyFetching) {

            if (organizationalUnit.getPerspective() != null) {
                organizationalUnitDTO.setPerspective(PerspectiveMapper.toDTO(organizationalUnit.getPerspective(), true));
            }
            if (organizationalUnit.getChildren() != null) {
                Set<OrganizationalUnitDTO> organizationalUnitDTOs = new HashSet<>();
                List<OrganizationalUnit> organizationalUnitList = new ArrayList<>(organizationalUnit.getChildren());
                if (organizationalUnitList != null) {
                    for (OrganizationalUnit ou : organizationalUnitList) {
                        organizationalUnitDTOs.add(OrganizationalUnitMapper.toDTO(ou, true));
                    }
                }
                organizationalUnitDTO.setChildren(organizationalUnitDTOs);
            }
        }
        return organizationalUnitDTO;
    }

    /**
     * Converts the JPA entity for an organizational unit into a DTO. The references to the perspective and children
     * are included in the DTO.
     *
     * @param organizationalUnit the organizational unit JPA entity
     * @return the DTO
     */
    public static OrganizationalUnitDTO toDTO(OrganizationalUnit organizationalUnit) {
        return toDTO(organizationalUnit, false);
    }

    /**
     * Converts the Neo4J entity for an organizational unit into a DTO.
     *
     * @param organizationalUnitNeo the organizational unit Neo4J entity
     * @return the DTO
     */
    public static OrganizationalUnitDTO toDTO(ro.teamnet.ou.domain.neo.OrganizationalUnit organizationalUnitNeo) {

        OrganizationalUnitDTO organizationalUnitDTO = new OrganizationalUnitDTO();

        organizationalUnitDTO.setId(organizationalUnitNeo.getJpaId());
        organizationalUnitDTO.setCode(organizationalUnitNeo.getCode());

        if (organizationalUnitNeo.getParent() != null) {
            organizationalUnitDTO.setParent(OrganizationalUnitMapper.toDTO(organizationalUnitNeo.getParent()));
        }
        Set<OrganizationalUnitDTO> organizationalUnitDTOs = new HashSet<>();
        List<ro.teamnet.ou.domain.neo.OrganizationalUnit> organizationalUnitNeoList = new ArrayList<>(organizationalUnitNeo.getChildren());

        if (organizationalUnitNeoList != null) {
            for (ro.teamnet.ou.domain.neo.OrganizationalUnit ouNeo : organizationalUnitNeoList) {
                organizationalUnitDTOs.add(OrganizationalUnitMapper.toDTO(ouNeo));
            }
        }
        organizationalUnitDTO.setChildren(organizationalUnitDTOs);

        return organizationalUnitDTO;
    }

    /**
     * Converts the Neo4J entity for an organizational unit into a DTO.
     * @param lazyFetching if true, the references to perspective and children wil not be added to the entity
     * @param organizationalUnitNeo the organizational unit Neo4J entity
     * @return the DTO
     */
    public static OrganizationalUnitDTO toDTO(ro.teamnet.ou.domain.neo.OrganizationalUnit organizationalUnitNeo, boolean lazyFetching) {

        OrganizationalUnitDTO organizationalUnitDTO = new OrganizationalUnitDTO();

        organizationalUnitDTO.setId(organizationalUnitNeo.getJpaId());
        organizationalUnitDTO.setCode(organizationalUnitNeo.getCode());

        if (organizationalUnitNeo.getParent() != null) {
            organizationalUnitDTO.setParent(OrganizationalUnitMapper.toDTO(organizationalUnitNeo.getParent(), true));
        }

        if (!lazyFetching) {
            Set<OrganizationalUnitDTO> organizationalUnitDTOs = new HashSet<>();
            List<ro.teamnet.ou.domain.neo.OrganizationalUnit> organizationalUnitNeoList = new ArrayList<>(organizationalUnitNeo.getChildren());

            if (organizationalUnitNeoList != null) {
                for (ro.teamnet.ou.domain.neo.OrganizationalUnit ouNeo : organizationalUnitNeoList) {
                    organizationalUnitDTOs.add(OrganizationalUnitMapper.toDTO(ouNeo, true));
                }
            }
            organizationalUnitDTO.setChildren(organizationalUnitDTOs);

        }
        return organizationalUnitDTO;
    }
}
