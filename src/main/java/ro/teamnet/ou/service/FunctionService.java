package ro.teamnet.ou.service;

import ro.teamnet.bootstrap.service.AbstractService;
import ro.teamnet.ou.domain.jpa.Function;
import ro.teamnet.ou.web.rest.dto.FunctionDTO;

import java.util.Set;

public interface FunctionService extends AbstractService<Function,Long> {

    public Function getOne(Long id);

    public Function update(Function function);

    public Function update(Function applicationRole, FunctionDTO functionDTO);

    public Boolean updateRoleById(Long id, FunctionDTO functionDTO);

    public Function getOneById(Long id);

    Set<Function> getAllWithModuleRights();

}
