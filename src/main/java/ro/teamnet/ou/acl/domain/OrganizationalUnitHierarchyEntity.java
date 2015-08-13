package ro.teamnet.ou.acl.domain;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

/**
 * Abstract superclass of entities owned by an organizational unit.
 * Data owned by a child organizational unit is also owned by its parent.
 * Provides filtering of all data based on the organizational units associated with the authenticated account.
 */
@MappedSuperclass
@FilterDef(
        name = "filterByOwnerOrganizationalUnits",
        parameters = @ParamDef(name = "ownerOrganizationalUnitIds", type = "long"),
        defaultCondition = "OWNER_OU_ID in (:ownerOrganizationalUnitIds)"
)
@Filter(name = "filterByOwnerOrganizationalUnits")
public abstract class OrganizationalUnitHierarchyEntity {

    @Column(name = "OWNER_OU_ID", nullable = false)
    private Long ownerOrganizationalUnitId;

    public Long getOwnerOrganizationalUnitId() {
        return ownerOrganizationalUnitId;
    }

    public void setOwnerOrganizationalUnitId(Long ownerOrganizationalUnitId) {
        this.ownerOrganizationalUnitId = ownerOrganizationalUnitId;
    }
}
