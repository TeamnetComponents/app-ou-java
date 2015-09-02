package ro.teamnet.ou.acl.domain;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * Embeddable class for the owner organizational unit id, used in data filtering.
 * @see OrganizationalUnitHierarchyEntity
 */
@Embeddable
public class DataOwner {

    @Column(name = "OWNER_OU_ID", nullable = true)
    private Long ownerOrganizationalUnitId;

    public Long getOwnerOrganizationalUnitId() {
        return ownerOrganizationalUnitId;
    }

    public void setOwnerOrganizationalUnitId(Long ownerOrganizationalUnitId) {
        this.ownerOrganizationalUnitId = ownerOrganizationalUnitId;
    }
}
