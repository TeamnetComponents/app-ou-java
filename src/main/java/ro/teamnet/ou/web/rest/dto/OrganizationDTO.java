package ro.teamnet.ou.web.rest.dto;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class OrganizationDTO implements Serializable {

    private Long id;
    private Long jpaId;
    private String code;
    private String description;
    private Date validFrom;
    private Date validTo;
    private Boolean active;
    private Set<PerspectiveDTO> perspectives;
    private Set<OrganizationalUnitDTO> roots;

    public OrganizationDTO() {
    }

    public OrganizationDTO(Long id, Long jpaId, String code, String description, Date validFrom, Date validTo, Boolean active,
                           Set<PerspectiveDTO> perspectives, Set<OrganizationalUnitDTO> roots) {
        this.id = id;
        this.jpaId = jpaId;
        this.code = code;
        this.description = description;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.active = active;
        this.perspectives = perspectives;
        this.roots = roots;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getJpaId() {
        return jpaId;
    }

    public void setJpaId(Long jpaId) {
        this.jpaId = jpaId;
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

    public Set<PerspectiveDTO> getPerspectives() {
        return perspectives;
    }

    public void setPerspectives(Set<PerspectiveDTO> perspectives) {
        this.perspectives = perspectives;
    }

    public Set<OrganizationalUnitDTO> getRoots() {
        return roots;
    }

    public void setRoots(Set<OrganizationalUnitDTO> roots) {
        this.roots = roots;
    }

    @Override
    public String toString() {
        return "OrganizationDTO{" +
                "id=" + id +
                ", jpaId=" + jpaId +
                ", code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", validFrom=" + validFrom +
                ", validTo=" + validTo +
                ", active=" + active +
                ", perspectives=" + perspectives +
                ", roots=" + roots +
                '}';
    }
}
