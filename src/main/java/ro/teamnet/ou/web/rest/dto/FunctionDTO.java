package ro.teamnet.ou.web.rest.dto;

import ro.teamnet.bootstrap.web.rest.dto.ModuleRightDTO;
import ro.teamnet.ou.domain.neo.Account;
import ro.teamnet.ou.domain.neo.OrganizationalUnit;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;


public class FunctionDTO implements Serializable {


    private Long neoId;
    private Long jpaId;
    private String code;
    private String description;
    private Date validFrom;
    private Date validTo;
    private Boolean active;
    private Set<ModuleRightDTO> moduleRights = new HashSet<>();

    private Account account;
    private OrganizationalUnit organizationalUnit;

    public FunctionDTO() {
    }

    public FunctionDTO(Long neoId, String code, String description, Date validFrom, Date validTo, Boolean active, Long jpaId,
                       Set<ModuleRightDTO> moduleRights, Account account, OrganizationalUnit organizationalUnit) {
        this.neoId = neoId;
        this.code = code;
        this.description = description;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.active = active;
        this.moduleRights = moduleRights;
        this.jpaId = jpaId;
        this.account = account;
        this.organizationalUnit = organizationalUnit;
    }

    public Long getNeoId() {
        return neoId;
    }

    public void setNeoId(Long neoId) {
        this.neoId = neoId;
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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public OrganizationalUnit getOrganizationalUnit() {
        return organizationalUnit;
    }

    public void setOrganizationalUnit(OrganizationalUnit organizationalUnit) {
        this.organizationalUnit = organizationalUnit;
    }

    public Long getJpaId() {
        return jpaId;
    }

    public void setJpaId(Long jpaId) {
        this.jpaId = jpaId;
    }

    @Override
    public String toString() {
        return "FunctionDTO{" +
                "neoId=" + neoId +
                ", jpaId=" + jpaId +
                ", code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", validFrom=" + validFrom +
                ", validTo=" + validTo +
                ", active=" + active +
                ", moduleRights=" + moduleRights +
                ", account=" + account +
                ", organizationalUnit=" + organizationalUnit +
                '}';
    }
}


