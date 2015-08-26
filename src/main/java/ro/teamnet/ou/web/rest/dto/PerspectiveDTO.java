package ro.teamnet.ou.web.rest.dto;

import java.io.Serializable;
import java.util.Set;

public class PerspectiveDTO implements Serializable {

    private Long id;
    private String code;
    private String description;

    private OrganizationDTO organizationDto;
    private OrganizationalUnitDTO organizationalUnit;
    private Set<OrganizationalUnitDTO> organizationalUnitSet;

    public PerspectiveDTO(){}

    public PerspectiveDTO(Long id, String code, String description, OrganizationDTO organizationDto,
                          OrganizationalUnitDTO organizationalUnit, Set<OrganizationalUnitDTO> organizationalUnitSet){

        this.id = id;
        this.code = code;
        this.description = description;
        this.organizationDto = organizationDto;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public OrganizationDTO getOrganizationDto() {
        return organizationDto;
    }

    public void setOrganizationDto(OrganizationDTO organizationDto) {
        this.organizationDto = organizationDto;
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

    @Override
    public String toString() {
        return "PerspectiveDTO{" +
                ", id=" + id +
                ", code='" + code + '\'' +
                ", description='" + description + '\'' +
                ", organizationDto=" + organizationDto +
                ", organizationalUnit=" + organizationalUnit +
                ", organizationalUnitSet=" + organizationalUnitSet +
                '}';
    }
}
