package ro.teamnet.ou.service;

import org.json.JSONArray;
import org.json.JSONObject;
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

    OrganizationalUnitDTO save(OrganizationalUnitDTO organizationalUnitDTO);

    void delete(Long id);

    OrganizationalUnitDTO getOneById(Long id);

    Set<OrganizationalUnitDTO> getAllOrganizationalUnit();

    Set<ro.teamnet.ou.domain.neo.OrganizationalUnit> getOrganizationalUnitTreeById(Long id);

    String getTree(Long id);
}
