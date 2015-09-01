package ro.teamnet.ou.web.rest.dto;

/**
 * Created by ionut.patrascu on 19.08.2015.
 */

import java.util.Set;

public class AccountDTO {

    private long id;
    private String username;
    private Set<FunctionDTO> functions;
    private Set<FunctionDTO> availableFunctions;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Set<FunctionDTO> getFunctions() {
        return functions;
    }

    public void setFunctions(Set<FunctionDTO> functions) {
        this.functions = functions;
    }

    public Set<FunctionDTO> getAvailableFunctions() {
        return availableFunctions;
    }

    public void setAvailableFunctions(Set<FunctionDTO> availableFunctions) {
        this.availableFunctions = availableFunctions;
    }
}
