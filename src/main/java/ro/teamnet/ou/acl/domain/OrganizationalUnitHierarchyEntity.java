package ro.teamnet.ou.acl.domain;

import org.hibernate.annotations.*;

import javax.persistence.Embedded;
import javax.persistence.MappedSuperclass;

/**
 * Abstract superclass of entities owned by an organizational unit.
 * Data owned by a child organizational unit is also owned by its parent.
 * Provides filtering of all data based on the organizational units associated with the authenticated account.
 */
@MappedSuperclass
@FilterDefs({
        @FilterDef(
                name = OrganizationalUnitHierarchyEntity.FILTER_BY_OWNER_ORGANIZATIONAL_UNITS,
                parameters = @ParamDef(name = OrganizationalUnitHierarchyEntity.OWNER_ORGANIZATIONAL_UNIT_IDS, type = "long"),
                defaultCondition = "OWNER_OU_ID in (:ownerOrganizationalUnitIds)"
        ),
        @FilterDef(
                name = OrganizationalUnitHierarchyEntity.FILTER_ALL_WHEN_NO_ORGANIZATIONAL_UNITS_ON_ACCOUNT,
                defaultCondition = "0=1"
        )
})
@Filters({
        @Filter(name = OrganizationalUnitHierarchyEntity.FILTER_BY_OWNER_ORGANIZATIONAL_UNITS),
        @Filter(name = OrganizationalUnitHierarchyEntity.FILTER_ALL_WHEN_NO_ORGANIZATIONAL_UNITS_ON_ACCOUNT)
})
public abstract class OrganizationalUnitHierarchyEntity {

    public static final String FILTER_BY_OWNER_ORGANIZATIONAL_UNITS = "filterByOwnerOrganizationalUnits";
    public static final String OWNER_ORGANIZATIONAL_UNIT_IDS = "ownerOrganizationalUnitIds";
    public static final String FILTER_ALL_WHEN_NO_ORGANIZATIONAL_UNITS_ON_ACCOUNT = "filterAllWhenNoOrganizationalUnitsOnAccount";

    @Embedded
    private DataOwner dataOwner;

    public DataOwner getDataOwner() {
        return dataOwner;
    }

    public void setDataOwner(DataOwner dataOwner) {
        this.dataOwner = dataOwner;
    }
}
