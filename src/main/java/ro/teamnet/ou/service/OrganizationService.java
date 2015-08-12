package ro.teamnet.ou.service;

import ro.teamnet.ou.domain.jpa.Organization;
import ro.teamnet.ou.web.rest.dto.OrganizationDTO;
import ro.teamnet.ou.web.rest.dto.PerspectiveDTO;

import java.util.Date;
import java.util.List;
import java.util.Set;

/**
 * Created by ionut.patrascu on 31.07.2015.
 */
public interface OrganizationService {

    OrganizationDTO create(OrganizationDTO organizationDTO);

    OrganizationDTO update(Organization organization, ro.teamnet.ou.domain.neo.Organization organizationNeo, OrganizationDTO organizationDTO);

    OrganizationDTO createOrganizationDTO(Long id, String code, String description, Date validFrom, Date validTo, Set<PerspectiveDTO> perspectiveDTOSet);

    OrganizationDTO toOrganizationDTO(Organization organization, ro.teamnet.ou.domain.neo.Organization organizationNeo);

    Set<OrganizationDTO> getAllOrganizationDTO();

    Organization updateOrganization(Organization organization, OrganizationDTO organizationDTO);

    List<Organization> getAllOrganization();

    Organization findOrganizationById(Long id);

    ro.teamnet.ou.domain.neo.Organization updateOrganizationNeo(ro.teamnet.ou.domain.neo.Organization organization, OrganizationDTO organizationDTO);

    ro.teamnet.ou.domain.neo.Organization findOrganizationNeoById(Long id);

    List<ro.teamnet.ou.domain.neo.Organization> getAllOrganizationNeo();
}
