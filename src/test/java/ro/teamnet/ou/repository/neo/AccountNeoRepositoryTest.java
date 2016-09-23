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
public class AccountNeoRepositoryTest {

    OuNeoUtil ouNeoGenericService = new OuNeoUtilImpl();

    @Inject
    AccountNeoRepository accountNeoRepository;

    private Account account;

    @Before
    public void initTest() {
        account = ouNeoGenericService.createAccount("NumeTest", "PrenumeTest", 1l);
    }

    @Test
    @Transactional(value="neo4jTransactionManager")
    public void createAccount() {
        Account testAccount = accountNeoRepository.save(account);
        Account accountFound = accountNeoRepository.findOne(testAccount.getId());

        assertThat(testAccount.getId()).isEqualTo(accountFound.getId());
        assertThat(testAccount.getUsername()).isEqualTo(accountFound.getUsername());

        accountNeoRepository.delete(accountFound);
    }

    @Test
    @Transactional(value="neo4jTransactionManager")
    public void updateAccount() {
        Account testAccount = accountNeoRepository.save(account);

        testAccount.setUsername("UsernameUpdated");
        accountNeoRepository.save(testAccount);

        Account accountFound = accountNeoRepository.findOne(testAccount.getId());

        assertThat(testAccount.getId()).isEqualTo(accountFound.getId());
        assertThat(testAccount.getUsername()).isEqualTo(accountFound.getUsername());

        accountNeoRepository.delete(accountFound);
    }

    @Test
    @Transactional(value="neo4jTransactionManager")
    public void deleteAccount() {
        Account testAccount = accountNeoRepository.save(account);

        accountNeoRepository.delete(testAccount);
        Account accountFound = accountNeoRepository.findOne(testAccount.getId());

        Assert.assertEquals(null, accountFound);
    }
}
