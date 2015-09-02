package ro.teamnet.ou.acl.domain;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;

/**
 * Abstract superclass of entities owned by an organizational unit.
 * Data owned by a child organizational unit is also owned by its parent.
 * Provides filtering of all data based on the organizational units associated with the authenticated account.
 */
@MappedSuperclass
@FilterDef(
        name = OrganizationalUnitHierarchyEntity.FILTER_BY_OWNER_ORGANIZATIONAL_UNITS,
        parameters = @ParamDef(name = OrganizationalUnitHierarchyEntity.OWNER_ORGANIZATIONAL_UNIT_IDS, type = "long"),
        defaultCondition = "OWNER_OU_ID in (:ownerOrganizationalUnitIds)"
)
@Filter(name = OrganizationalUnitHierarchyEntity.FILTER_BY_OWNER_ORGANIZATIONAL_UNITS)
public abstract class OrganizationalUnitHierarchyEntity {

    public static final String FILTER_BY_OWNER_ORGANIZATIONAL_UNITS = "filterByOwnerOrganizationalUnits";
    public static final String OWNER_ORGANIZATIONAL_UNIT_IDS = "ownerOrganizationalUnitIds";

    @Embedded
    private OrganizationalUnitHierarchy organizationalUnitHierarchy;

    public OrganizationalUnitHierarchy getOrganizationalUnitHierarchy() {
        return organizationalUnitHierarchy;
    }

    public void setOrganizationalUnitHierarchy(OrganizationalUnitHierarchy organizationalUnitHierarchy) {
        this.organizationalUnitHierarchy = organizationalUnitHierarchy;
    }
}
