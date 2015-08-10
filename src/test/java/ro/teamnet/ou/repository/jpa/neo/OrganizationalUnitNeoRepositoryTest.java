package ro.teamnet.ou.repository.jpa.neo;

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
import ro.teamnet.ou.repository.neo.OrganizationalUnitNeoRepository;
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
    @Transactional
    public void createOrganizationalUnit() {
        OrganizationalUnit testOrganizationalUnit = organizationalUnitNeoRepository.save(organizationalUnit);
        OrganizationalUnit organizationalUnitFound = organizationalUnitNeoRepository.findOne(testOrganizationalUnit.getId());

        assertThat(testOrganizationalUnit.getId()).isEqualTo(organizationalUnitFound.getId());
        assertThat(testOrganizationalUnit.getCode()).isEqualTo(organizationalUnitFound.getCode());

        organizationalUnitNeoRepository.delete(organizationalUnitFound);
    }

    @Test
    @Transactional
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
    @Transactional
    public void deleteOrganizationalUnit() {
        OrganizationalUnit testOrganizationalUnit = organizationalUnitNeoRepository.save(organizationalUnit);

        organizationalUnitNeoRepository.delete(testOrganizationalUnit);
        OrganizationalUnit organizationalUnitFound = organizationalUnitNeoRepository.findOne(testOrganizationalUnit.getId());

        Assert.assertEquals(null, organizationalUnitFound);
    }
}
