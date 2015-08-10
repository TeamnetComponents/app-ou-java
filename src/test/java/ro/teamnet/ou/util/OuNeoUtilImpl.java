package ro.teamnet.ou.util;

import ro.teamnet.ou.domain.neo.*;

/**
 * Created by Marian.Spoiala on 8/10/2015.
 */
public class OuNeoUtilImpl implements OuNeoUtil {

    @Override
    public Organization createOrganization(String code, Long jpaId) {
        Organization organization = new Organization();
        organization.setCode(code);
        organization.setJpaId(jpaId);
        return organization;
    }

    @Override
    public Perspective createPerspective(String code, Organization organization, OrganizationalUnit organizationalUnit, Long jpaId) {
        Perspective perspective = new Perspective();
        perspective.setCode(code);
        perspective.setOrganization(organization);
        perspective.setOrganizationalUnit(organizationalUnit);
        perspective.setJpaId(jpaId);
        return perspective;
    }

    @Override
    public Perspective createPerspective(String code, Long jpaId) {
        Perspective perspective = new Perspective();
        perspective.setCode(code);
        perspective.setJpaId(jpaId);
        return perspective;
    }

    @Override
    public OrganizationalUnit createOrganizationalUnit(String code, Long jpaId) {
        OrganizationalUnit organizationalUnit = new OrganizationalUnit();
        organizationalUnit.setCode(code);
        organizationalUnit.setJpaId(jpaId);
        return organizationalUnit;
    }

    @Override
    public Function createFunction(String code, Account account, OrganizationalUnit organizationalUnit, Long jpaId) {
        Function function = new Function();
        function.setCode(code);
        function.setOrganizationalUnit(organizationalUnit);
        function.setAccount(account);
        function.setJpaId(jpaId);
        return function;
    }

    @Override
    public Function createFunction(String code, Long jpaId) {
        Function function = new Function();
        function.setCode(code);
        function.setJpaId(jpaId);
        return function;
    }

    @Override
    public Account createAccount(String firstName, String lastName, Long jpaId) {
        Account account = new Account();
        account.setFirstName(firstName);
        account.setLastName(lastName);
        account.setJpaId(jpaId);
        return account;
    }
}
