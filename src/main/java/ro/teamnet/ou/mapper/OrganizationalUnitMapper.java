package ro.teamnet.ou.mapper;

import ro.teamnet.ou.domain.jpa.OrganizationalUnit;
import ro.teamnet.ou.web.rest.dto.OrganizationalUnitDTO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class OrganizationalUnitMapper {

    public static OrganizationalUnit toJPA(OrganizationalUnitDTO organizationalUnitDTO) {
        OrganizationalUnit organizationalUnit = new OrganizationalUnit();

        organizationalUnit.setId(organizationalUnitDTO.getId());
        organizationalUnit.setCode(organizationalUnitDTO.getCode());
        organizationalUnit.setDescription(organizationalUnitDTO.getDescription());
        organizationalUnit.setValidFrom(organizationalUnitDTO.getValidFrom());
        organizationalUnit.setValidTo(organizationalUnitDTO.getValidTo());
        organizationalUnit.setActive(organizationalUnitDTO.getActive());
        if(organizationalUnitDTO.getParent() != null)
            organizationalUnit.setParent(OrganizationalUnitMapper.toJPA(organizationalUnitDTO.getParent()));

        Set<OrganizationalUnit> organizationalUnitSet = new HashSet<>();
        if(organizationalUnitDTO.getChildren() != null) {
            for (OrganizationalUnitDTO item : organizationalUnitDTO.getChildren()) {
                OrganizationalUnit organizationalUnitItem = OrganizationalUnitMapper.toJPA(item);
                organizationalUnitSet.add(organizationalUnitItem);
            }
        }

        organizationalUnit.setChildren(organizationalUnitSet);
        if(organizationalUnitDTO.getPerspective()!=null)
            organizationalUnit.setPerspective(PerspectiveMapper.toJPA(organizationalUnitDTO.getPerspective()));

        return organizationalUnit;
    }

    public static ro.teamnet.ou.domain.neo.OrganizationalUnit toNeo(OrganizationalUnitDTO organizationalUnitDTO) {
        ro.teamnet.ou.domain.neo.OrganizationalUnit organizationalUnit = new ro.teamnet.ou.domain.neo.OrganizationalUnit();

        organizationalUnit.setId(organizationalUnitDTO.getId());
        organizationalUnit.setJpaId(organizationalUnitDTO.getJpaId());
        organizationalUnit.setCode(organizationalUnitDTO.getCode());
        if(organizationalUnitDTO.getParent() != null)
            organizationalUnit.setParent(OrganizationalUnitMapper.toNeo(organizationalUnitDTO.getParent()));

        Set<ro.teamnet.ou.domain.neo.OrganizationalUnit> organizationalUnitSet = new HashSet<>();
        if(organizationalUnitDTO.getChildren() != null) {
            for (OrganizationalUnitDTO item : organizationalUnitDTO.getChildren()) {
                ro.teamnet.ou.domain.neo.OrganizationalUnit organizationalUnitItem = OrganizationalUnitMapper.toNeo(item);
                organizationalUnitSet.add(organizationalUnitItem);
            }
        }

        organizationalUnit.setChildren(organizationalUnitSet);
        organizationalUnit.setAccounts(organizationalUnitDTO.getAccounts());

        return organizationalUnit;
    }

    public static OrganizationalUnitDTO toDTO(OrganizationalUnit organizationalUnit, ro.teamnet.ou.domain.neo.OrganizationalUnit organizationalUnitNeo) {
        OrganizationalUnitDTO organizationalUnitDTO = new OrganizationalUnitDTO();

        organizationalUnitDTO.setId(organizationalUnit.getId());
        organizationalUnitDTO.setJpaId(organizationalUnit.getId());
        organizationalUnitDTO.setCode(organizationalUnit.getCode());
        organizationalUnitDTO.setDescription(organizationalUnit.getDescription());
        organizationalUnitDTO.setValidFrom(organizationalUnit.getValidFrom());
        organizationalUnitDTO.setValidTo(organizationalUnit.getValidTo());
        organizationalUnitDTO.setActive(organizationalUnit.getActive());
        if(organizationalUnit.getParent() != null && organizationalUnitNeo.getParent() != null)
            organizationalUnitDTO.setParent(OrganizationalUnitMapper.toDTO(organizationalUnit.getParent(), organizationalUnitNeo.getParent()));

        organizationalUnitDTO.setAccounts(organizationalUnitNeo.getAccounts());
        if(organizationalUnit.getPerspective() != null)
            organizationalUnitDTO.setPerspective(PerspectiveMapper.toDTO(organizationalUnit.getPerspective(), null));

        Set<OrganizationalUnitDTO> organizationalUnitDTOs = new HashSet<>();
        List<OrganizationalUnit> organizationalUnitList = new ArrayList<>(organizationalUnit.getChildren());
        List<ro.teamnet.ou.domain.neo.OrganizationalUnit> organizationalUnitNeoList = new ArrayList<>(organizationalUnitNeo.getChildren());
        if(organizationalUnitList != null) {
            for (int i = 0; i < organizationalUnitList.size(); i++) {
                organizationalUnitDTOs.add(OrganizationalUnitMapper.toDTO(organizationalUnitList.get(i), organizationalUnitNeoList.get(i)));
            }
        }
        organizationalUnitDTO.setChildren(organizationalUnitDTOs);

        return organizationalUnitDTO;
    }
}
