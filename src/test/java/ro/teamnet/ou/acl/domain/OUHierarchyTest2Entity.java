package ro.teamnet.ou.acl.domain;

import javax.persistence.*;

@Entity
@Table(name = "_TEST_OUHIERARCHY2")
public class OUHierarchyTest2Entity extends OrganizationalUnitHierarchyEntity{
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
