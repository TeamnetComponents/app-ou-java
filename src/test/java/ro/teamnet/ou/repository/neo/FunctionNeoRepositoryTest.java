package ro.teamnet.ou.repository.neo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ro.teamnet.ou.domain.neo.Account;
import ro.teamnet.ou.domain.neo.Function;
import ro.teamnet.ou.domain.neo.OrganizationalUnit;
import ro.teamnet.ou.mock.OuTestApplication;
import ro.teamnet.ou.util.OuNeoUtil;
import ro.teamnet.ou.util.OuNeoUtilImpl;

import javax.inject.Inject;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Marian.Spoiala on 8/10/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = OuTestApplication.class)
@WebAppConfiguration
@IntegrationTest
@ActiveProfiles("test")
public class FunctionNeoRepositoryTest {

    OuNeoUtil ouNeoGenericService = new OuNeoUtilImpl();

    @Inject
    private FunctionNeoRepository functionNeoRepository;

    @Inject
    private AccountNeoRepository accountNeoRepository;

    @Inject
    private OrganizationalUnitNeoRepository organizationalUnitNeoRepository;

    private Function function;
    private Account account;
    private OrganizationalUnit organizationalUnit;

    @Before
    public void initTest() {
        account = ouNeoGenericService.createAccount("NumeTest", "PrenumeTest", 1l);
        organizationalUnit = ouNeoGenericService.createOrganizationalUnit("OrganizationalUnitTest", 1l);
        function = ouNeoGenericService.createFunction("FunctionTest", 1l);
    }

    @Test
    @Transactional
    public void createFunction() {
        Account testAccount = accountNeoRepository.save(account);
        OrganizationalUnit testOrganizationalUnit = organizationalUnitNeoRepository.save(organizationalUnit);

        function.setAccount(testAccount);
        function.setOrganizationalUnit(testOrganizationalUnit);
        Function testFunction = functionNeoRepository.save(function);

        Function functionFound = functionNeoRepository.findOne(testFunction.getId());

        assertThat(testFunction.getId()).isEqualTo(functionFound.getId());
        assertThat(testFunction.getCode()).isEqualTo(functionFound.getCode());
        assertThat(testFunction.getJpaId()).isEqualTo(functionFound.getJpaId());
        assertThat(testFunction.getAccount().getId()).isEqualTo(functionFound.getAccount().getId());
        assertThat(testFunction.getOrganizationalUnit().getId()).isEqualTo(functionFound.getOrganizationalUnit().getId());

        functionNeoRepository.delete(functionFound);
        accountNeoRepository.delete(testAccount);
        organizationalUnitNeoRepository.delete(testOrganizationalUnit);
    }

    @Test
    @Transactional
    public void updateFunction() {
        Account testAccount = accountNeoRepository.save(account);
        OrganizationalUnit testOrganizationalUnit = organizationalUnitNeoRepository.save(organizationalUnit);

        function.setAccount(testAccount);
        function.setOrganizationalUnit(testOrganizationalUnit);
        Function testFunction = functionNeoRepository.save(function);

        testFunction.setCode("FunctionTestUpdated");
        functionNeoRepository.save(testFunction);

        Function functionFound = functionNeoRepository.findOne(testFunction.getId());

        assertThat(testFunction.getId()).isEqualTo(functionFound.getId());
        assertThat(testFunction.getCode()).isEqualTo(functionFound.getCode());
        assertThat(testFunction.getJpaId()).isEqualTo(functionFound.getJpaId());
        assertThat(testFunction.getAccount().getId()).isEqualTo(functionFound.getAccount().getId());
        assertThat(testFunction.getOrganizationalUnit().getId()).isEqualTo(functionFound.getOrganizationalUnit().getId());

        functionNeoRepository.delete(functionFound);
        accountNeoRepository.delete(testAccount);
        organizationalUnitNeoRepository.delete(testOrganizationalUnit);
    }

    @Test
    @Transactional
    public void deleteFunction() {
        Account testAccount = accountNeoRepository.save(account);
        OrganizationalUnit testOrganizationalUnit = organizationalUnitNeoRepository.save(organizationalUnit);

        function.setAccount(testAccount);
        function.setOrganizationalUnit(testOrganizationalUnit);
        Function testFunction = functionNeoRepository.save(function);

        functionNeoRepository.delete(testFunction);
        Function functionFound = functionNeoRepository.findOne(function.getId());

        Assert.assertEquals(null, functionFound);

        accountNeoRepository.delete(testAccount);
        organizationalUnitNeoRepository.delete(testOrganizationalUnit);
    }
}
