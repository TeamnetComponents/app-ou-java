package ro.teamnet.ou.web.rest.dto;

import ro.teamnet.bootstrap.web.rest.dto.ModuleRightDTO;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


public class FunctionDTO implements Serializable {


    private Long id;
    private String code;
    private String description;
    private Date validFrom;
    private Date validTo;
    private Boolean active;
    private Set<ModuleRightDTO> moduleRights = new HashSet<>();
    private AccountDTO account;
    private OrganizationalUnitDTO organizationalUnit;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getValidTo() {
        return validTo;
    }

    public void setValidTo(Date validTo) {
        this.validTo = validTo;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setModuleRights(Set<ModuleRightDTO> moduleRights) {
        this.moduleRights = moduleRights;
    }

    public Set<ModuleRightDTO> getModuleRights() {
        return moduleRights;
    }

    public AccountDTO getAccount() {
        return account;
    }

    public void setAccount(AccountDTO account) {
        this.account = account;
    }

    public OrganizationalUnitDTO getOrganizationalUnit() {
        return organizationalUnit;
    }

    public void setOrganizationalUnit(OrganizationalUnitDTO organizationalUnit) {
        this.organizationalUnit = organizationalUnit;
    }
}


