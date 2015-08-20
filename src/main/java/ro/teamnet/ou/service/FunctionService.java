package ro.teamnet.ou.service;

import ro.teamnet.ou.web.rest.dto.FunctionDTO;

import java.util.Set;

public interface FunctionService {

    FunctionDTO save(FunctionDTO function);

    void delete(Long id);

    FunctionDTO findOne(Long id);

    Set<FunctionDTO> findAll();

}
