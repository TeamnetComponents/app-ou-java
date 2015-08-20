package ro.teamnet.ou.web.rest.dto;

/**
 * Created by ionut.patrascu on 19.08.2015.
 */

import org.springframework.security.core.GrantedAuthority;
import ro.teamnet.bootstrap.domain.Account;
import ro.teamnet.bootstrap.domain.ApplicationRole;
import ro.teamnet.bootstrap.domain.Module;
import ro.teamnet.bootstrap.domain.ModuleRight;
import ro.teamnet.bootstrap.domain.util.ModuleRightSource;
import ro.teamnet.bootstrap.web.rest.dto.ModuleDTO;
import ro.teamnet.bootstrap.web.rest.dto.ModuleRightDTO;
import ro.teamnet.bootstrap.web.rest.dto.RoleDTO;
import ro.teamnet.ou.domain.neo.Function;
import ro.teamnet.ou.domain.neo.OrganizationalUnit;

import javax.validation.constraints.Pattern;
import java.util.*;

public class AccountDTO {

    private long id;

    @Pattern(regexp = "^[a-z0-9]*$")
    private String login;
    private String password;
    private String firstName;
    private String lastName;
    private String email;
    private String langKey;
    private String gender;
    private Boolean activated;
    private Set<RoleDTO> roles = new HashSet<>();
    private HashMap<String, ModuleRightDTO> moduleRights = new HashMap<>();
    private Set<OrganizationalUnit> organizationalUnits;
    private Set<Function> functions;


    public AccountDTO() {
    }

    public AccountDTO(Account account,Collection<GrantedAuthority> moduleRightSet){
        this.id = account.getId();
        this.login = account.getLogin();
        this.password = account.getPassword();
        this.firstName = account.getFirstName();
        this.lastName = account.getLastName();
        this.email = account.getEmail();
        this.langKey = account.getLangKey();
        this.gender = account.getGender();

        for(GrantedAuthority grantedAuthority: moduleRightSet) {
            if(grantedAuthority instanceof ModuleRight && ((ModuleRight)grantedAuthority).getModule() != null){
                ModuleRight mr=(ModuleRight)grantedAuthority;
                moduleRights.put(
                        mr.getModule().getCode()+"_"+mr.getModuleRightCode(),
                        loadModuleRightDTO(mr, ModuleRightSource.ACCOUNT.name())
                );
            }else if(grantedAuthority instanceof ApplicationRole){
                ApplicationRole applicationRole = (ApplicationRole) grantedAuthority;
                roles.add(new RoleDTO(applicationRole.getId(), applicationRole.getVersion(), applicationRole.getCode(), applicationRole.getDescription(),
                        applicationRole.getOrder(), applicationRole.getValidFrom(), applicationRole.getValidTo(), applicationRole.getActive(), applicationRole.getLocal(), null));
            }
        }
    }

    private ModuleRightDTO loadModuleRightDTO(ModuleRight mr, String source) {
        Module module = mr.getModule();

        ModuleDTO moduleDTO = new ModuleDTO(module.getId(), module.getVersion(), module.getCode(),
                module.getDescription(), module.getType(), module.getParentModule(), null);

        return new ModuleRightDTO(mr.getId(), mr.getVersion(), mr.getRight(), moduleDTO, source);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLangKey() {
        return langKey;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Boolean getActivated() {
        return activated;
    }

    public void setActivated(Boolean activated) {
        this.activated = activated;
    }

    public Set<RoleDTO> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleDTO> roles) {
        this.roles = roles;
    }

    public HashMap<String, ModuleRightDTO> getModuleRights() {
        return moduleRights;
    }

    public void setModuleRights(HashMap<String, ModuleRightDTO> moduleRights) {
        this.moduleRights = moduleRights;
    }

    public void setOrganizationalUnits(Set<OrganizationalUnit> organizationalUnits) {
        this.organizationalUnits = organizationalUnits;
    }

    public void setFunctions(Set<Function> functions) {
        this.functions = functions;
    }

    public Set<OrganizationalUnit> getOrganizationalUnits() {
        return organizationalUnits;
    }

    public Set<Function> getFunctions() {
        return functions;
    }

    @Override
    public String toString() {
        return "AccountDTO{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", langKey='" + langKey + '\'' +
                ", gender='" + gender + '\'' +
                ", activated=" + activated +
                ", roles=" + roles +
                ", moduleRights=" + moduleRights +
                ", organizationalUnits=" + organizationalUnits +
                ", functions=" + functions +
                '}';
    }
}
