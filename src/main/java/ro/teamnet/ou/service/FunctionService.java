package ro.teamnet.ou.service;

import ro.teamnet.bootstrap.service.AbstractService;
import ro.teamnet.ou.domain.jpa.Function;
import ro.teamnet.ou.web.rest.dto.FunctionDTO;

import java.util.Set;

public interface FunctionService {

    public FunctionDTO save(FunctionDTO function);

    void delete(Long id);

    public FunctionDTO getOneById(Long id);

    Set<FunctionDTO> getAllWithModuleRights();

}
