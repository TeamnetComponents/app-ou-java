package ro.teamnet.ou.service;

import ro.teamnet.ou.web.rest.dto.FunctionDTO;

import java.util.Set;

public interface FunctionService {

    FunctionDTO save(FunctionDTO function);

    void delete(Long id);

    FunctionDTO findOne(Long id);

    Set<FunctionDTO> findAll();

    Set<FunctionDTO> findAllByAccountId(Long accountId);

    void addToAccount(Long accountId, FunctionDTO functionDTO);

    void removeFromAccount(Long accountId, Long functionId);

    Set<FunctionDTO> findAllByOrganizationalUnitId(Long ouId);

    void addToOrganizationalUnit(Long ouId, FunctionDTO functionDTO);

    void removeFromOrganizationalUnit(Long ouId, Long functionId);
}
