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
import ro.teamnet.ou.domain.neo.Perspective;
import ro.teamnet.ou.domain.neo.Organization;
import ro.teamnet.ou.domain.neo.OrganizationalUnit;
import ro.teamnet.ou.mock.OuTestApplication;
import ro.teamnet.ou.repository.neo.OrganizationNeoRepository;
import ro.teamnet.ou.repository.neo.PerspectiveNeoRepository;
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
public class PerspectiveNeoRepositoryTest {

    OuNeoUtil ouNeoGenericService = new OuNeoUtilImpl();

    @Inject
    private PerspectiveNeoRepository perspectiveNeoRepository;

    @Inject
    private OrganizationNeoRepository organizationNeoRepository;

    @Inject
    private OrganizationalUnitNeoRepository organizationalUnitNeoRepository;

    private Perspective perspective;
    private Organization organization;
    private OrganizationalUnit organizationalUnit;

    @Before
    public void initTest() {
        organization = ouNeoGenericService.createOrganization("OrganizationTest", 1l);
        organizationalUnit = ouNeoGenericService.createOrganizationalUnit("OrganizationalUnitTest", 1l);
        perspective = ouNeoGenericService.createPerspective("PerspectiveTest", 1l);
    }

    @Test
    @Transactional
    public void createPerspective() {
        Organization testOrganization = organizationNeoRepository.save(organization);
        OrganizationalUnit testOrganizationalUnit = organizationalUnitNeoRepository.save(organizationalUnit);

        perspective.setOrganization(testOrganization);
        perspective.setOrganizationalUnit(testOrganizationalUnit);
        Perspective testPerspective = perspectiveNeoRepository.save(perspective);

        Perspective perspectiveFound = perspectiveNeoRepository.findOne(testPerspective.getId());

        assertThat(testPerspective.getId()).isEqualTo(perspectiveFound.getId());
        assertThat(testPerspective.getCode()).isEqualTo(perspectiveFound.getCode());
        assertThat(testPerspective.getJpaId()).isEqualTo(perspectiveFound.getJpaId());
        assertThat(testPerspective.getOrganization().getId()).isEqualTo(perspectiveFound.getOrganization().getId());
        assertThat(testPerspective.getOrganizationalUnit().getId()).isEqualTo(perspectiveFound.getOrganizationalUnit().getId());

        perspectiveNeoRepository.delete(perspectiveFound);
        organizationNeoRepository.delete(testOrganization);
        organizationalUnitNeoRepository.delete(testOrganizationalUnit);
    }

    @Test
    @Transactional
    public void updatePerspective() {
        Organization testOrganization = organizationNeoRepository.save(organization);
        OrganizationalUnit testOrganizationalUnit = organizationalUnitNeoRepository.save(organizationalUnit);

        perspective.setOrganization(testOrganization);
        perspective.setOrganizationalUnit(testOrganizationalUnit);
        Perspective testPerspective = perspectiveNeoRepository.save(perspective);

        testPerspective.setCode("PerspectiveTestUpdated");
        perspectiveNeoRepository.save(testPerspective);

        Perspective perspectiveFound = perspectiveNeoRepository.findOne(testPerspective.getId());

        assertThat(testPerspective.getId()).isEqualTo(perspectiveFound.getId());
        assertThat(testPerspective.getCode()).isEqualTo(perspectiveFound.getCode());
        assertThat(testPerspective.getJpaId()).isEqualTo(perspectiveFound.getJpaId());
        assertThat(testPerspective.getOrganization().getId()).isEqualTo(perspectiveFound.getOrganization().getId());
        assertThat(testPerspective.getOrganizationalUnit().getId()).isEqualTo(perspectiveFound.getOrganizationalUnit().getId());

        perspectiveNeoRepository.delete(perspectiveFound);
        organizationNeoRepository.delete(testOrganization);
        organizationalUnitNeoRepository.delete(testOrganizationalUnit);
    }

    @Test
    @Transactional
    public void deletePerspective() {
        Organization testOrganization = organizationNeoRepository.save(organization);
        OrganizationalUnit testOrganizationalUnit = organizationalUnitNeoRepository.save(organizationalUnit);

        perspective.setOrganization(testOrganization);
        perspective.setOrganizationalUnit(testOrganizationalUnit);
        Perspective testPerspective = perspectiveNeoRepository.save(perspective);

        perspectiveNeoRepository.delete(testPerspective);
        Perspective perspectiveFound = perspectiveNeoRepository.findOne(perspective.getId());

        Assert.assertEquals(null, perspectiveFound);

        organizationNeoRepository.delete(testOrganization);
        organizationalUnitNeoRepository.delete(testOrganizationalUnit);
    }
}
