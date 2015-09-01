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
import ro.teamnet.ou.web.rest.dto.PerspectiveDTO;

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
        organizationalUnitDTO = saveJPA(organizationalUnitDTO);
        saveNeo(organizationalUnitDTO);
        return organizationalUnitDTO;
    }

    @Override
    public OrganizationalUnitDTO saveOUTreeRoot(PerspectiveDTO perspective) {
        OrganizationalUnit ouTreeRoot = new OrganizationalUnit();
        ouTreeRoot.setPerspective(perspectiveRepository.findOne(perspective.getId()));
        ouTreeRoot.setCode(perspective.getCode() + "_ROOT");
        ouTreeRoot.setActive(true);
        organizationalUnitRepository.save(ouTreeRoot);
        OrganizationalUnitDTO organizationalUnitDTO = OrganizationalUnitMapper.toDTO(ouTreeRoot);
        saveNeo(organizationalUnitDTO);
        return organizationalUnitDTO;
    }

    private void saveNeo(OrganizationalUnitDTO organizationalUnitDTO) {
        ro.teamnet.ou.domain.neo.OrganizationalUnit organizationalUnit = OrganizationalUnitMapper.toNeo(organizationalUnitDTO);

        if(organizationalUnitDTO.getParent() != null) {
            organizationalUnit.setParent(organizationalUnitNeoRepository.findByJpaId(organizationalUnitDTO.getParent().getId()));
        }
        ro.teamnet.ou.domain.neo.OrganizationalUnit existingNeoOu = organizationalUnitNeoRepository.findByJpaId(organizationalUnitDTO.getId());
        if (existingNeoOu != null) {
            organizationalUnit.setId(existingNeoOu.getId());
        }
        organizationalUnitNeoRepository.save(organizationalUnit);
    }

    private OrganizationalUnitDTO saveJPA(OrganizationalUnitDTO organizationalUnitDTO) {
        OrganizationalUnit organizationalUnit = OrganizationalUnitMapper.toJPA(organizationalUnitDTO);
        organizationalUnitRepository.save(organizationalUnit);
        organizationalUnitDTO.setId(organizationalUnit.getId());
        return organizationalUnitDTO;
    }

    @Override
    public void delete(Long id) {
        organizationalUnitRepository.delete(id);

        ro.teamnet.ou.domain.neo.OrganizationalUnit organizationalUnitNeo = organizationalUnitNeoRepository.findByJpaId(id);
        if (organizationalUnitNeo != null) {
            organizationalUnitNeoRepository.delete(organizationalUnitNeo.getId());
        }
    }

    @Override
    public OrganizationalUnitDTO findOne(Long id) {
        OrganizationalUnit organizationalUnit = organizationalUnitRepository.findOne(id);
        return OrganizationalUnitMapper.toDTO(organizationalUnit);
    }

    @Override
    public Set<OrganizationalUnitDTO> findAll() {
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
    public Set<ro.teamnet.ou.domain.neo.OrganizationalUnit> getOrganizationalUnitTreeById(Long id) {
        return organizationalUnitNeoRepository.getOrganizationalUnitTreeById(id);
    }

    private JSONObject bfs(ro.teamnet.ou.domain.neo.OrganizationalUnit rootNode,
                   List<ro.teamnet.ou.domain.neo.OrganizationalUnit> orgUnitList,
                   List<Long> marked,
                   HashMap<Long, Long> map) {

        JSONArray arrayJSON = new JSONArray();
        JSONObject nodeJSON = new JSONObject();

        for (ro.teamnet.ou.domain.neo.OrganizationalUnit childNode : rootNode.getChildren()) {
            childNode = orgUnitList.get(map.get(childNode.getId()).intValue());
            if (!marked.contains(childNode.getId())) {
                marked.add(childNode.getId());
                JSONObject childrenObjectJSON = bfs(childNode, orgUnitList, marked, map);
                arrayJSON.put(childrenObjectJSON);
            }
        }

        try {
            nodeJSON.put("id", rootNode.getJpaId());
            nodeJSON.put("code", rootNode.getCode());
            nodeJSON.put("accounts", rootNode.getAccounts());
            nodeJSON.put("children", arrayJSON);

            JSONObject parentNodeJSON = new JSONObject();
            if (rootNode.getParent() != null) {
                parentNodeJSON.put("id", rootNode.getParent().getJpaId());
                parentNodeJSON.put("code", rootNode.getParent().getCode());
            }
            nodeJSON.put("parent", parentNodeJSON);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return nodeJSON;
    }

    public String getTree(Long rootId) {
        ro.teamnet.ou.domain.neo.OrganizationalUnit rootOu = organizationalUnitNeoRepository.findByJpaId(rootId);
        Set<ro.teamnet.ou.domain.neo.OrganizationalUnit> orgUnitSet = getOrganizationalUnitTreeById(rootOu.getId());
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
        arrayJSON.put(bfs(orgUnitList.get(map.get(rootOu.getId()).intValue()), orgUnitList, marked, map));
        return arrayJSON.toString();
    }

    public List<OrganizationalUnitDTO> getParentOrgUnitsById(Long rootId, Long id) {
        ro.teamnet.ou.domain.neo.OrganizationalUnit rootOu = organizationalUnitNeoRepository.findByJpaId(rootId);
        ro.teamnet.ou.domain.neo.OrganizationalUnit nodeOu = organizationalUnitNeoRepository.findByJpaId(id);

        Set<ro.teamnet.ou.domain.neo.OrganizationalUnit> orgUnitSet = organizationalUnitNeoRepository.getParentOrgUnitsById(rootOu.getId(), nodeOu.getId());

        List<OrganizationalUnitDTO> organizationalUnitDTOs = new ArrayList<>();
        for (ro.teamnet.ou.domain.neo.OrganizationalUnit orgUnit : orgUnitSet) {
            organizationalUnitDTOs.add(OrganizationalUnitMapper.toDTO(orgUnit, false));
        }

        return organizationalUnitDTOs;
    }

}
