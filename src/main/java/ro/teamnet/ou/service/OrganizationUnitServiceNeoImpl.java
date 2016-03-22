package ro.teamnet.ou.service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ro.teamnet.ou.domain.neo.OrganizationalUnit;
import ro.teamnet.ou.repository.neo.OrganizationalUnitNeoRepository;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


@Service
public class OrganizationUnitServiceNeoImpl implements OrganizationalUnitServiceNeo{
    @Inject
    private OrganizationalUnitNeoRepository organizationalUnitNeoRepository;

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

    @Transactional(value = "neo4jTransactionManager",readOnly = true)
    public String getTree(Long rootId) {
        ro.teamnet.ou.domain.neo.OrganizationalUnit rootOu = organizationalUnitNeoRepository.findByJpaId(rootId);
        Set<OrganizationalUnit> orgUnitSet =organizationalUnitNeoRepository.getOrganizationalUnitTreeById(rootOu.getId());
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
}
