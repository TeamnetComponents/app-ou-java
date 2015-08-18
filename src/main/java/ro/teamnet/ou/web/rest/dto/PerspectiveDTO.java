package ro.teamnet.ou.web.rest.dto;

import java.io.Serializable;
import java.util.Set;

public class PerspectiveDTO implements Serializable {

    private Long neoId;
    private Long jpaId;
    private String code;
    private String description;


    private OrganizationDTO organization;
    private OrganizationalUnitDTO organizationalUnit;
    private Set<OrganizationalUnitDTO> organizationalUnitSet;

    public PerspectiveDTO(){}

    public PerspectiveDTO(Long neoId, String code, String description, OrganizationDTO organization,
                          OrganizationalUnitDTO organizationalUnit, Set<OrganizationalUnitDTO> organizationalUnitSet){
        this.neoId = neoId;
        this.code = code;
        this.description = description;
        this.organization = organization;
        this.organizationalUnit = organizationalUnit;
        this.organizationalUnitSet = organizationalUnitSet;
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

    public Long getJpaId() {
        return jpaId;
    }

    public void setJpaId(Long jpaId) {
        this.jpaId = jpaId;
    }

    public OrganizationDTO getOrganization() {
        return organization;
    }

    public void setOrganization(OrganizationDTO organization) {
        this.organization = organization;
    }

    public OrganizationalUnitDTO getOrganizationalUnit() {
        return organizationalUnit;
    }

    public void setOrganizationalUnit(OrganizationalUnitDTO organizationalUnit) {
        this.organizationalUnit = organizationalUnit;
    }

    public Set<OrganizationalUnitDTO> getOrganizationalUnitSet() {
        return organizationalUnitSet;
    }

    public void setOrganizationalUnitSet(Set<OrganizationalUnitDTO> organizationalUnitSet) {
        this.organizationalUnitSet = organizationalUnitSet;
    }

    public Long getNeoId() {
        return neoId;
    }

    public void setNeoId(Long neoId) {
        this.neoId = neoId;
    }

    @Override
    public String toString() {
        return "PerspectiveDTO{" +
                "neoId=" + neoId +
                ", jpaId=" + jpaId +
                ", code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", organization=" + organization +
                ", organizationalUnit=" + organizationalUnit +
                ", organizationalUnitSet=" + organizationalUnitSet +
                '}';
    }
}
