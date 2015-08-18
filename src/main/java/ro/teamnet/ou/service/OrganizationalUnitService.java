package ro.teamnet.ou.service;

import ro.teamnet.bootstrap.service.AbstractService;
import ro.teamnet.ou.domain.jpa.OrganizationalUnit;
import ro.teamnet.ou.domain.jpa.Perspective;
import ro.teamnet.ou.web.rest.dto.OrganizationalUnitDTO;
import ro.teamnet.ou.web.rest.dto.PerspectiveDTO;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by ionut.patrascu on 31.07.2015.
 */
public interface OrganizationalUnitService {
//extends AbstractService<OrganizationalUnit, Long> {

//    OrganizationalUnitDTO createOrganizationalUnitDTO(Long id, String code, String description, Date validFrom, Date validTo, PerspectiveDTO perspectiveDTO,
//                                                             OrganizationalUnitDTO parent, Set<OrganizationalUnitDTO> children);

    OrganizationalUnitDTO findOrganizationalUnitById(Long id);

    Set<OrganizationalUnitDTO> getAllOrganizationalUnit();

    OrganizationalUnitDTO save(OrganizationalUnitDTO organizationalUnitDTO);

}
