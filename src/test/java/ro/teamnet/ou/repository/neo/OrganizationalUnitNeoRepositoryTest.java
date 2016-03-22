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
import ro.teamnet.ou.domain.neo.OrganizationalUnit;
import ro.teamnet.ou.mock.OuTestApplication;
import ro.teamnet.ou.util.OuNeoUtil;
import ro.teamnet.ou.util.OuNeoUtilImpl;

import javax.inject.Inject;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Marian.Spoiala on 8/10/2015.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = OuTestApplication.class)
@WebAppConfiguration
@IntegrationTest
@ActiveProfiles("test")
public class OrganizationalUnitNeoRepositoryTest {

    OuNeoUtil ouNeoGenericService = new OuNeoUtilImpl();

    @Inject
    OrganizationalUnitNeoRepository organizationalUnitNeoRepository;

    private OrganizationalUnit organizationalUnit;

    @Before
    public void initTest() {
        organizationalUnit = ouNeoGenericService.createOrganizationalUnit("OrganizationalUnitTest", 1l);
    }

    @Test
    @Transactional(value="neo4jTransactionManager")
    public void createOrganizationalUnit() {
        OrganizationalUnit testOrganizationalUnit = organizationalUnitNeoRepository.save(organizationalUnit);
        OrganizationalUnit organizationalUnitFound = organizationalUnitNeoRepository.findOne(testOrganizationalUnit.getId());

        assertThat(testOrganizationalUnit.getId()).isEqualTo(organizationalUnitFound.getId());
        assertThat(testOrganizationalUnit.getCode()).isEqualTo(organizationalUnitFound.getCode());

        organizationalUnitNeoRepository.delete(organizationalUnitFound);
    }

    @Test
    @Transactional(value="neo4jTransactionManager")
    public void updateOrganizationalUnit() {
        OrganizationalUnit testOrganizationalUnit = organizationalUnitNeoRepository.save(organizationalUnit);

        testOrganizationalUnit.setCode("PrenumeUpdated");
        organizationalUnitNeoRepository.save(testOrganizationalUnit);

        OrganizationalUnit organizationalUnitFound = organizationalUnitNeoRepository.findOne(testOrganizationalUnit.getId());

        assertThat(testOrganizationalUnit.getId()).isEqualTo(organizationalUnitFound.getId());
        assertThat(testOrganizationalUnit.getCode()).isEqualTo(organizationalUnitFound.getCode());

        organizationalUnitNeoRepository.delete(organizationalUnitFound);
    }

    @Test
    @Transactional(value="neo4jTransactionManager")
    public void deleteOrganizationalUnit() {
        OrganizationalUnit testOrganizationalUnit = organizationalUnitNeoRepository.save(organizationalUnit);

        organizationalUnitNeoRepository.delete(testOrganizationalUnit);
        OrganizationalUnit organizationalUnitFound = organizationalUnitNeoRepository.findOne(testOrganizationalUnit.getId());

        Assert.assertEquals(null, organizationalUnitFound);
    }

    @Test
    @Transactional(value="neo4jTransactionManager")
    public void getOrganizationalUnitTreeById() {
        OrganizationalUnit organizationalUnit1 = ouNeoGenericService.createOrganizationalUnit("OrganizationalUnitTest1", 1l);
        OrganizationalUnit organizationalUnit2 = ouNeoGenericService.createOrganizationalUnit("OrganizationalUnitTest2", 2l);
        OrganizationalUnit organizationalUnit3 = ouNeoGenericService.createOrganizationalUnit("OrganizationalUnitTest3", 3l);
        OrganizationalUnit organizationalUnit4 = ouNeoGenericService.createOrganizationalUnit("OrganizationalUnitTest4", 4l);

        organizationalUnit1 = organizationalUnitNeoRepository.save(organizationalUnit1);

        organizationalUnit2.setParent(organizationalUnit1);
        organizationalUnit3.setParent(organizationalUnit1);
        organizationalUnit4.setParent(organizationalUnit2);
        organizationalUnit2 = organizationalUnitNeoRepository.save(organizationalUnit2);
        organizationalUnit3 = organizationalUnitNeoRepository.save(organizationalUnit3);
        organizationalUnit4 = organizationalUnitNeoRepository.save(organizationalUnit4);

        Set<OrganizationalUnit> lista = organizationalUnitNeoRepository.getOrganizationalUnitTreeById(organizationalUnit1.getId());
        assertThat(lista.size()).isEqualTo(4);

        organizationalUnitNeoRepository.delete(organizationalUnit1);
        organizationalUnitNeoRepository.delete(organizationalUnit2);
        organizationalUnitNeoRepository.delete(organizationalUnit3);
        organizationalUnitNeoRepository.delete(organizationalUnit4);
    }

    @Test
    @Transactional(value="neo4jTransactionManager")
    public void getOrganizationalUnitSubTreeJpaIdsByRootJpaId() {
        OrganizationalUnit organizationalUnit1 = ouNeoGenericService.createOrganizationalUnit("OrganizationalUnitTest1", 1l);
        OrganizationalUnit organizationalUnit2 = ouNeoGenericService.createOrganizationalUnit("OrganizationalUnitTest2", 2l);
        OrganizationalUnit organizationalUnit3 = ouNeoGenericService.createOrganizationalUnit("OrganizationalUnitTest3", 3l);
        OrganizationalUnit organizationalUnit4 = ouNeoGenericService.createOrganizationalUnit("OrganizationalUnitTest4", 4l);

        organizationalUnit1 = organizationalUnitNeoRepository.save(organizationalUnit1);

        organizationalUnit2.setParent(organizationalUnit1);
        organizationalUnit3.setParent(organizationalUnit1);
        organizationalUnit4.setParent(organizationalUnit2);
        organizationalUnit2 = organizationalUnitNeoRepository.save(organizationalUnit2);
        organizationalUnit3 = organizationalUnitNeoRepository.save(organizationalUnit3);
        organizationalUnit4 = organizationalUnitNeoRepository.save(organizationalUnit4);

        Set<Long> lista = organizationalUnitNeoRepository.getOrganizationalUnitSubTreeJpaIdsByRootJpaId(organizationalUnit1.getJpaId());
        assertThat(lista.size()).isEqualTo(3);

        organizationalUnitNeoRepository.delete(organizationalUnit1);
        organizationalUnitNeoRepository.delete(organizationalUnit2);
        organizationalUnitNeoRepository.delete(organizationalUnit3);
        organizationalUnitNeoRepository.delete(organizationalUnit4);
    }
}
