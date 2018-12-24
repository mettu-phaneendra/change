package integration.com.change.healthcare;


import com.change.healthcare.ApplicationTest;
import com.change.healthcare.service.ValidationService;
import com.change.healthcare.service.ValidationServiceImpl;
import com.change.healthcare.utils.PasswordValidationUtil;
import com.change.healthcare.utils.ValidationUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
//@WebMvcTest(ValidationController.class)
@SpringBootTest(classes = ApplicationTest.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class PasswordValidationTest {

    @Autowired
    WebApplicationContext wac;

    private MockMvc mvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        this.mvc = MockMvcBuilders.webAppContextSetup(this.wac).dispatchOptions(true).build();
    }

    @TestConfiguration
    static class PasswordValidationTestConfig {
        @Bean
        public ValidationService validationService() {
            return new ValidationServiceImpl();
        }

        @Bean
        public ValidationUtil validationUtil() {
            return new PasswordValidationUtil();
        }
    }

    @Test
    public void testHappyPath() throws Exception {
        mvc.perform(get("/validate/password/abcdefg12")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testAlphaNumeric() throws Exception {
        mvc.perform(get("/validate/password/abcdefg")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testLength() throws Exception {
        mvc.perform(get("/validate/password/abc")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

    @Test
    public void testSequence() throws Exception {
        mvc.perform(get("/validate/password/abcabc1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

}
