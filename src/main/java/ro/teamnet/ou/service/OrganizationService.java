package ro.teamnet.ou.service;

import ro.teamnet.ou.domain.jpa.Organization;
import ro.teamnet.ou.web.rest.dto.OrganizationDTO;

import java.util.Set;

/**
 * Created by ionut.patrascu on 31.07.2015.
 */
public interface OrganizationService {

    OrganizationDTO save(OrganizationDTO organizationDTO);

    void delete(Long id);

    Set<OrganizationDTO> getAllOrganizationDTOs();

    OrganizationDTO findOrganizationDTOById(Long id);
}
