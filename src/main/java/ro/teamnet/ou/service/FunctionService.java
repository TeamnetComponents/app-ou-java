package ro.teamnet.ou.service;

import ro.teamnet.ou.web.rest.dto.FunctionDTO;
import ro.teamnet.ou.web.rest.dto.FunctionRelationshipDTO;

import java.util.Set;

public interface FunctionService {

    FunctionDTO save(FunctionDTO function);

    void delete(Long id);

    FunctionDTO findOne(Long id);

    Set<FunctionDTO> findAll();

    FunctionRelationshipDTO addRelationship(FunctionRelationshipDTO functionRelationshipDTO);

    void deleteRelationship(Long functionRelationshipId);

    Set<FunctionDTO> findAllByAccountId(Long accountId);

    void addToAccount(Long accountId, FunctionDTO functionDTO);

    void removeFromAccount(Long accountId, Long functionId);
}
