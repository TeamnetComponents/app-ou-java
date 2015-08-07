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
import ro.teamnet.ou.mock.OuTestApplication;
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

    @Test
    @Transactional
    public void createFunctionTest() throws Exception {
        Assert.assertTrue(functionRepository.findAll().isEmpty());
        System.out.println("abc");
        Function function = new Function();
        function.setCode(TEST_CODE);
        function.setValidFrom(new Date());
        function.setValidTo(new Date());
        function.setActive(true);

        functionService.save(function);
//        mockMvc.perform(post("/app/rest/function")
//                .contentType(TestUtil.APPLICATION_JSON_UTF8)
//                .content(TestUtil.convertObjectToJsonBytes(function)))
//                .andExpect(status().isOk());

        List<Function> all = functionRepository.findAll();

        System.out.println(all.size());
        Assert.assertFalse(all.isEmpty());
        Assert.assertEquals(1, all.size());
        Assert.assertEquals(TEST_CODE, all.get(0).getCode());
    }
}
