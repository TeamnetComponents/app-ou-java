package ro.teamnet.ou.domain.jpa;


import ro.teamnet.bootstrap.domain.RoleBase;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("FUNCTION")
public class Function extends RoleBase{
}
