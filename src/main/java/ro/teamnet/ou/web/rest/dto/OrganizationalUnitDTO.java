package ro.teamnet.ou.web.rest.dto;

import ro.teamnet.ou.domain.neo.Account;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class OrganizationalUnitDTO implements Serializable {

    private Long neoId;
    private Long jpaId;
    private String code;
    private String description;
    private Date validFrom;
    private Date validTo;
    private Boolean active;

    private PerspectiveDTO perspective;
    private OrganizationalUnitDTO parent;
    private Set<OrganizationalUnitDTO> children = new HashSet<>();


    private Set<Account> accounts = new HashSet<>();
//    private Set<OrganizationalUnitFunction> organizationalUnitFunctions = new HashSet<>();

    public OrganizationalUnitDTO() {
    }

    public OrganizationalUnitDTO(Long neoId, String code, String description, Date validFrom, Date validTo,
                                 Boolean active, Long jpaId, PerspectiveDTO perspective, OrganizationalUnitDTO parent,
                                 Set<OrganizationalUnitDTO> children, Set<Account> accounts) {
        this.neoId = neoId;
        this.code = code;
        this.description = description;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.active = active;
        this.jpaId = jpaId;
        this.perspective = perspective;
        this.parent = parent;
        this.children = children;
        this.accounts = accounts;
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

    public PerspectiveDTO getPerspective() {
        return perspective;
    }

    public void setPerspective(PerspectiveDTO perspective) {
        this.perspective = perspective;
    }

    public OrganizationalUnitDTO getParent() {
        return parent;
    }

    public void setParent(OrganizationalUnitDTO parent) {
        this.parent = parent;
    }

    public Set<OrganizationalUnitDTO> getChildren() {
        return children;
    }

    public void setChildren(Set<OrganizationalUnitDTO> children) {
        this.children = children;
    }

    public Long getJpaId() {
        return jpaId;
    }

    public void setJpaId(Long jpaId) {
        this.jpaId = jpaId;
    }

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    @Override
    public String toString() {
        return "OrganizationalUnitDTO{" +
                "neoId=" + neoId +
                ", jpaId=" + jpaId +
                ", code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", validFrom=" + validFrom +
                ", validTo=" + validTo +
                ", active=" + active +
                ", perspective=" + perspective +
                ", parent=" + parent +
                ", children=" + children +
                ", accounts=" + accounts +
                '}';
    }
}
