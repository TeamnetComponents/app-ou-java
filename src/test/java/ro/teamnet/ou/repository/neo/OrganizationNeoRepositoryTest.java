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
import ro.teamnet.ou.domain.neo.Organization;
import ro.teamnet.ou.mock.OuTestApplication;
import ro.teamnet.ou.repository.neo.OrganizationNeoRepository;
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
public class OrganizationNeoRepositoryTest {

    OuNeoUtil ouNeoGenericService = new OuNeoUtilImpl();

    @Inject
    OrganizationNeoRepository organizationNeoRepository;

    private Organization organization;

    @Before
    public void initTest() {
        organization = ouNeoGenericService.createOrganization("OrganizationTest", 1l);
    }

    @Test
    @Transactional
    public void createOrganization() {
        Organization testOrganization = organizationNeoRepository.save(organization);
        Organization organizationFound = organizationNeoRepository.findOne(testOrganization.getId());

        assertThat(testOrganization.getId()).isEqualTo(organizationFound.getId());
        assertThat(testOrganization.getCode()).isEqualTo(organizationFound.getCode());

        organizationNeoRepository.delete(organizationFound);
    }

    @Test
    @Transactional
    public void updateOrganization() {
        Organization testOrganization = organizationNeoRepository.save(organization);

        testOrganization.setCode("PrenumeUpdated");
        organizationNeoRepository.save(testOrganization);

        Organization organizationFound = organizationNeoRepository.findOne(testOrganization.getId());

        assertThat(testOrganization.getId()).isEqualTo(organizationFound.getId());
        assertThat(testOrganization.getCode()).isEqualTo(organizationFound.getCode());

        organizationNeoRepository.delete(organizationFound);
    }

    @Test
    @Transactional
    public void deleteOrganization() {
        Organization testOrganization = organizationNeoRepository.save(organization);

        organizationNeoRepository.delete(testOrganization);
        Organization organizationFound = organizationNeoRepository.findOne(testOrganization.getId());

        Assert.assertEquals(null, organizationFound);
    }
}
