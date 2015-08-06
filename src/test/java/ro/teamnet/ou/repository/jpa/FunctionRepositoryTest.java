package ro.teamnet.ou.repository.jpa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import ro.teamnet.ou.domain.jpa.Function;
import ro.teamnet.ou.mock.OuTestApplication;

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
    FunctionRepository functionRepository;

    @Test
    public void createFunctionTest() {
        Function function = new Function();
        function.setCode(TEST_CODE);
        function.setValidFrom(new Date());
        function.setValidTo(new Date());
        function.setActive(true);

        functionRepository.save(function);

        List<Function> all = functionRepository.findAll();
//        Assert.assertFalse(all.isEmpty());
//        Assert.assertEquals(TEST_CODE, all.get(0).getCode());
    }
}
