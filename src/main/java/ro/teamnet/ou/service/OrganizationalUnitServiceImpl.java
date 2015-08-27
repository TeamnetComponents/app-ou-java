package ro.teamnet.ou.service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.teamnet.ou.domain.jpa.OrganizationalUnit;
import ro.teamnet.ou.mapper.OrganizationalUnitMapper;
import ro.teamnet.ou.repository.jpa.OrganizationalUnitRepository;
import ro.teamnet.ou.repository.jpa.PerspectiveRepository;
import ro.teamnet.ou.repository.neo.OrganizationalUnitNeoRepository;
import ro.teamnet.ou.web.rest.dto.OrganizationalUnitDTO;

import javax.inject.Inject;
import java.util.*;

/**
 * Created by Marian.Spoiala 20.08.2015
 */
@Service
@Transactional
public class OrganizationalUnitServiceImpl implements OrganizationalUnitService {

    @Inject
    private OrganizationalUnitRepository organizationalUnitRepository;

    @Inject
    private OrganizationalUnitNeoRepository organizationalUnitNeoRepository;

    @Inject
    public PerspectiveRepository perspectiveRepository;

    @Override
    public OrganizationalUnitDTO save(OrganizationalUnitDTO organizationalUnitDTO) {

        OrganizationalUnit organizationalUnit = OrganizationalUnitMapper.toJPA(organizationalUnitDTO);
        organizationalUnitRepository.save(organizationalUnit);

        ro.teamnet.ou.domain.neo.OrganizationalUnit organizationalUnitNeo = OrganizationalUnitMapper.toNeo(organizationalUnitDTO);
        organizationalUnitNeo.setJpaId(organizationalUnit.getId());

        Long neoId = organizationalUnitNeoRepository.findByJpaId(organizationalUnit.getId()).getId();
        organizationalUnitNeo.setId(neoId);

        organizationalUnitNeoRepository.save(organizationalUnitNeo);

        return OrganizationalUnitMapper.toDTO(organizationalUnit);
    }

    @Override
    public void delete(Long id) {
        OrganizationalUnit organizationalUnit = organizationalUnitRepository.findOne(id);
        organizationalUnitRepository.delete(organizationalUnit);

        ro.teamnet.ou.domain.neo.OrganizationalUnit organizationalUnitNeo = organizationalUnitNeoRepository.findByJpaId(id);
        Long neoId = organizationalUnitNeoRepository.findByJpaId(id).getId();
        organizationalUnitNeo.setId(neoId);

        organizationalUnitNeoRepository.delete(organizationalUnitNeo);
    }

    @Override
    @Transactional
    public OrganizationalUnitDTO getOneById(Long id) {

        OrganizationalUnit organizationalUnit = organizationalUnitRepository.findOne(id);
        ro.teamnet.ou.domain.neo.OrganizationalUnit organizationalUnitNeo = organizationalUnitNeoRepository.findOne(id);

        return OrganizationalUnitMapper.toDTO(organizationalUnit);
    }

    @Override
    @Transactional
    public Set<OrganizationalUnitDTO> getAllOrganizationalUnit() {
        List<OrganizationalUnit> organizationalUnits = organizationalUnitRepository.findAll();
        Set<ro.teamnet.ou.domain.neo.OrganizationalUnit> organizationalUnitsNeo = organizationalUnitNeoRepository.getAllOrganizationalUnits();

        Set<OrganizationalUnitDTO> organizationalUnitDTOs = new HashSet<>();
        if (organizationalUnits != null && organizationalUnitsNeo != null) {
            for (OrganizationalUnit organizationalUnit : organizationalUnits) {
                for (ro.teamnet.ou.domain.neo.OrganizationalUnit neoOu : organizationalUnitsNeo) {
                    if (organizationalUnit.getId().equals(neoOu.getJpaId())) {
                        organizationalUnitDTOs.add(OrganizationalUnitMapper.toDTO(organizationalUnit));
                    }
                }
            }
        }

        return organizationalUnitDTOs;
    }

    @Override
    @Transactional
    public Set<ro.teamnet.ou.domain.neo.OrganizationalUnit> getOrganizationalUnitTreeById(Long id) {
        return organizationalUnitNeoRepository.getOrganizationalUnitTreeById(id);
    }

    JSONObject BFS(ro.teamnet.ou.domain.neo.OrganizationalUnit orgUnit,
                  List<ro.teamnet.ou.domain.neo.OrganizationalUnit> orgUnitList,
                  List<Long> marked,
                  HashMap<Long, Long> map) {

        JSONArray arrayJSON = new JSONArray();
        JSONObject nodeJSON = new JSONObject();

        for (ro.teamnet.ou.domain.neo.OrganizationalUnit N : orgUnit.getChildren()) {
            N = orgUnitList.get(map.get(N.getId()).intValue());
            if ( marked.contains(N.getId()) == false) {
                marked.add(N.getId());
                JSONObject childrenObjectJSON = BFS(N, orgUnitList, marked, map);
                arrayJSON.put(childrenObjectJSON);
            }
        }

        try {
            nodeJSON.put("id", orgUnit.getJpaId());
            nodeJSON.put("code", orgUnit.getCode());
            nodeJSON.put("accounts", orgUnit.getAccounts());
            nodeJSON.put("children", arrayJSON);

            JSONObject parentNodeJSON = new JSONObject();
            parentNodeJSON.put("id", orgUnit.getParent().getJpaId());
            parentNodeJSON.put("code", orgUnit.getParent().getCode());

            nodeJSON.put("parent", parentNodeJSON);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return nodeJSON;
    }

    public String getTree(Long id) {

        Set<ro.teamnet.ou.domain.neo.OrganizationalUnit> orgUnitSet = getOrganizationalUnitTreeById(id);
        List<Long> marked = new ArrayList<>();
        List<ro.teamnet.ou.domain.neo.OrganizationalUnit> orgUnitList = new ArrayList<>();
        HashMap<Long, Long> map = new HashMap<>();

        long pos = 0;
        for (ro.teamnet.ou.domain.neo.OrganizationalUnit orgUnit : orgUnitSet) {
            orgUnitList.add(orgUnit);
            map.put(orgUnit.getId(), pos);
            pos++;
        }

        JSONArray arrayJSON = new JSONArray();
        arrayJSON.put(BFS(orgUnitList.get(map.get(id).intValue()), orgUnitList, marked, map));
        return arrayJSON.toString();
    }
}
