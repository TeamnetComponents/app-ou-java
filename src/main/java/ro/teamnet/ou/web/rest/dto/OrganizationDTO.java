package ro.teamnet.ou.web.rest.dto;

import ro.teamnet.ou.domain.Perspective;
import ro.teamnet.ou.domain.neo.OrganizationalUnit;


import java.io.Serializable;
import java.util.Date;
import java.util.Set;

public class OrganizationDTO implements Serializable {

    private Long id;
    private String code;
    private String description;
    private Date validFrom;
    private Date validTo;
    private Boolean active;

    private Long jpaId;
    private Set<Perspective> perspectives;
    private Set<OrganizationalUnit> roots;

    public OrganizationDTO(){}

    public OrganizationDTO(Long id, String code, String description, Date validFrom, Date validTo, Boolean active,
                           Long jpaId, Set<Perspective> perspectives, Set<OrganizationalUnit> roots){
        this.id = id;
        this.code = code;
        this.description = description;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.active = active;
        this.jpaId = jpaId;
        this.perspectives = perspectives;
        this.roots = roots;
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

    public Set<Perspective> getPerspectives() {
        return perspectives;
    }

    public void setPerspectives(Set<Perspective> perspectives) {
        this.perspectives = perspectives;
    }

    public Set<OrganizationalUnit> getRoots() {
        return roots;
    }

    public void setRoots(Set<OrganizationalUnit> roots) {
        this.roots = roots;
    }

    @Override
    public String toString() {
        return "OrganizationDTO{" +
                "id=" + id +
                ", code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", validFrom=" + validFrom +
                ", validTo=" + validTo +
                ", active=" + active +
                ", jpaId=" + jpaId +
                ", perspectives=" + perspectives +
                ", roots=" + roots +
                '}';
    }
}
