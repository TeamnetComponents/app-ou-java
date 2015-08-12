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
//extends AbstractService<Perspective, Long> {

    PerspectiveDTO createPerspectiveDTO(Long id, String code, String description, OrganizationDTO organizationDTO, Set<OrganizationalUnitDTO> organizationalUnitDTOSet);

    PerspectiveDTO toPerspectiveDTO(Perspective perspective, ro.teamnet.ou.domain.neo.Perspective perspectiveNeo);

    Perspective updatePerspective(Perspective perspective, PerspectiveDTO perspectiveDTO);

    ro.teamnet.ou.domain.neo.Perspective updatePerspectiveNeo(ro.teamnet.ou.domain.neo.Perspective perspective, PerspectiveDTO perspectiveDTO);

    Perspective findPerspectiveById(Long id);

    ro.teamnet.ou.domain.neo.Perspective findPerspectiveNeoById(Long id);

    List<Perspective> getAllPerspectives();

    List<ro.teamnet.ou.domain.neo.Perspective> getAllPerspectivesNeo();

    PerspectiveDTO create(PerspectiveDTO perspectiveDTO);

    PerspectiveDTO update(Perspective perspective, ro.teamnet.ou.domain.neo.Perspective perspectiveNeo, PerspectiveDTO perspectiveDTO);
}
