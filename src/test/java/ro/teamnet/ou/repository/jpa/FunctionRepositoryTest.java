package ro.teamnet.ou.repository.jpa;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;
import ro.teamnet.ou.domain.jpa.Function;
import ro.teamnet.ou.domain.neo.Account;
import ro.teamnet.ou.domain.neo.OrganizationalUnit;
import ro.teamnet.ou.mock.OuTestApplication;
import ro.teamnet.ou.repository.neo.AccountNeoRepository;
import ro.teamnet.ou.repository.neo.FunctionNeoRepository;
import ro.teamnet.ou.repository.neo.OrganizationalUnitNeoRepository;
import ro.teamnet.ou.service.FunctionService;

import javax.inject.Inject;
import java.util.Date;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = OuTestApplication.class)
@WebAppConfiguration
@IntegrationTest
@ActiveProfiles("test")
public class FunctionRepositoryTest {

    public static final String TEST_CODE = "TEST";

    @Inject
    private FunctionService functionService;
    @Inject
    private FunctionRepository functionRepository;

    @Inject
    private AccountNeoRepository accountNeoRepository;
    @Inject
    private OrganizationalUnitNeoRepository organizationalUnitNeoRepository;
    @Inject
    private FunctionNeoRepository functionNeoRepository;

    @Test
    @Transactional
    public void createFunctionTest() throws Exception {
        Assert.assertTrue(functionRepository.findAll().isEmpty());
        Function function = new Function();
        function.setCode(TEST_CODE);
        function.setValidFrom(new Date());
        function.setValidTo(new Date());
        function.setActive(true);

        functionService.save(function);
        List<Function> all = functionRepository.findAll();

        Assert.assertFalse(all.isEmpty());
        Assert.assertEquals(1, all.size());
        Assert.assertEquals(TEST_CODE, all.get(0).getCode());

        long initialAccountCount = accountNeoRepository.count();
        Account account = new Account();
        account.setJpaId(1l);
        account.setFirstName("TestFN");
        account.setLastName("TestLN");
        accountNeoRepository.save(account);
        Assert.assertEquals(initialAccountCount + 1, accountNeoRepository.count());

        long initialOuCount = organizationalUnitNeoRepository.count();
        OrganizationalUnit ou = new OrganizationalUnit();
        ou.setJpaId(1l);
        ou.setCode("TestOU");
        organizationalUnitNeoRepository.save(ou);
        Assert.assertEquals(initialOuCount + 1, organizationalUnitNeoRepository.count());

        accountNeoRepository.delete(account);
        organizationalUnitNeoRepository.delete(ou);
//        ro.teamnet.ou.domain.neo.Function neoFunction = new ro.teamnet.ou.domain.neo.Function();
//        neoFunction.setJpaId(function.getId());
//        neoFunction.setAccount(account);
//        neoFunction.setOrganizationalUnit(ou);
//        functionNeoRepository.save(neoFunction);
//
//        long count = functionNeoRepository.count();
//        System.out.println(count);

    }
}
