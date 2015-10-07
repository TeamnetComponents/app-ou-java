package ro.teamnet.ou.service;

import ro.teamnet.ou.domain.neo.OrganizationalUnit;
import ro.teamnet.ou.web.rest.dto.OrganizationDTO;

import java.util.Collection;
import java.util.Set;

/**
 * Created by ionut.patrascu on 31.07.2015.
 */
public interface OrganizationService {

    OrganizationDTO save(OrganizationDTO organizationDTO);

    OrganizationDTO update(OrganizationDTO organizationDTO);

    void delete(Long id);

    Set<OrganizationDTO> getAllOrganizationDTOs();

    OrganizationDTO findOrganizationDTOById(Long id);

    Collection<OrganizationDTO> getPublicOrganizations();

    Set<OrganizationalUnit> getOUsInOrganization(Long organizationId);
}
