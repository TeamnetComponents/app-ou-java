package ro.teamnet.ou.service;

import ro.teamnet.bootstrap.service.AbstractService;
import ro.teamnet.ou.domain.jpa.Perspective;
import ro.teamnet.ou.web.rest.dto.OrganizationDTO;
import ro.teamnet.ou.web.rest.dto.OrganizationalUnitDTO;
import ro.teamnet.ou.web.rest.dto.PerspectiveDTO;

import java.util.List;
import java.util.Set;

/**
 * Created by ionut.patrascu on 31.07.2015.
 */
public interface PerspectiveService {

    PerspectiveDTO findPerspectiveById(Long id);

    Set<PerspectiveDTO> findPerspectivesByOrganizationId(Long id);

    Set<PerspectiveDTO> getAllPerspectives();

    PerspectiveDTO save(PerspectiveDTO perspectiveDTO);

    PerspectiveDTO update(PerspectiveDTO perspectiveDTO);

    void delete(PerspectiveDTO perspectiveDTO);

}
