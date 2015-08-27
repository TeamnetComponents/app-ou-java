package ro.teamnet.ou.web.rest.dto;

import java.io.Serializable;

public class PerspectiveDTO implements Serializable {

    private Long id;
    private String code;
    private String description;

    private OrganizationDTO organization;
    private OrganizationalUnitDTO ouTreeRoot;

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

    public OrganizationDTO getOrganization() {
        return organization;
    }

    public void setOrganization(OrganizationDTO organization) {
        this.organization = organization;
    }

    public OrganizationalUnitDTO getOuTreeRoot() {
        return ouTreeRoot;
    }

    public void setOuTreeRoot(OrganizationalUnitDTO ouTreeRoot) {
        this.ouTreeRoot = ouTreeRoot;
    }
}
