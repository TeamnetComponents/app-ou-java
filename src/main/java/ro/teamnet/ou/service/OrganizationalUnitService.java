package ro.teamnet.ou.service;

import ro.teamnet.ou.web.rest.dto.OrganizationalUnitDTO;

import java.util.Set;

/**
 * Created by ionut.patrascu on 31.07.2015.
 */
public interface OrganizationalUnitService {

    OrganizationalUnitDTO save(OrganizationalUnitDTO organizationalUnitDTO);

    void delete(Long id);

    OrganizationalUnitDTO findOne(Long id);

    Set<OrganizationalUnitDTO> findAll();

    Set<ro.teamnet.ou.domain.neo.OrganizationalUnit> getOrganizationalUnitTreeById(Long id);

    String getTree(Long rootId);
}
