package ro.teamnet.ou.util;

import ro.teamnet.ou.domain.neo.*;

/**
 * Created by Marian.Spoiala on 8/10/2015.
 */
public interface OuNeoUtil {

    Organization createOrganization(String code, Long jpaId);

    Perspective createPerspective(String code, Long jpaId);

    Perspective createPerspective(String code, Organization organization, OrganizationalUnit organizationalUnit, Long jpaId);

    OrganizationalUnit createOrganizationalUnit(String code, Long jpaId);

    Function createFunction(String code, Long jpaId);

    Function createFunction(String code, Account account, OrganizationalUnit organizationalUnit, Long jpaId);

    Account createAccount(String firstName, String lastName, Long jpaId);
}
