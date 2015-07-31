package ro.teamnet.ou.web.rest.dto;

import ro.teamnet.ou.domain.Account;
import ro.teamnet.ou.domain.jpa.OrganizationalUnitFunction;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class OrganizationalUnitDTO implements Serializable {

    private Long id;
    private String code;
    private String description;
    private Date validFrom;
    private Date validTo;
    private Boolean active;

    private Long jpaId;
    private OrganizationalUnitDTO parent;
    private Set<OrganizationalUnitDTO> children = new HashSet<>();
    private Set<Account> accounts = new HashSet<>();

    private Set<OrganizationalUnitFunction> organizationalUnitFunctions = new HashSet<>();

    public OrganizationalUnitDTO() {
    }

    public OrganizationalUnitDTO(Long id, String code, String description, Date validFrom, Date validTo,
                                 Boolean active, Long jpaId, OrganizationalUnitDTO parent, Set<OrganizationalUnitDTO> children,
                                 Set<Account> accounts, Set<OrganizationalUnitFunction> organizationalUnitFunctions) {
        this.id = id;
        this.code = code;
        this.description = description;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.active = active;
        this.jpaId = jpaId;
        this.parent = parent;
        this.children = children;
        this.accounts = accounts;
        this.organizationalUnitFunctions = organizationalUnitFunctions;
    }

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

    public Long getJpaId() {
        return jpaId;
    }

    public void setJpaId(Long jpaId) {
        this.jpaId = jpaId;
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

    public Set<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(Set<Account> accounts) {
        this.accounts = accounts;
    }

    public Set<OrganizationalUnitFunction> getOrganizationalUnitFunctions() {
        return organizationalUnitFunctions;
    }

    public void setOrganizationalUnitFunctions(Set<OrganizationalUnitFunction> organizationalUnitFunctions) {
        this.organizationalUnitFunctions = organizationalUnitFunctions;
    }

    @Override
    public String toString() {
        return "OrganizationalUnitDTO{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", validFrom=" + validFrom +
                ", validTo=" + validTo +
                ", active=" + active +
                ", jpaId=" + jpaId +
                ", parent=" + parent +
                ", children=" + children +
                ", accounts=" + accounts +
                ", organizationalUnitFunctions=" + organizationalUnitFunctions +
                '}';
    }
}
