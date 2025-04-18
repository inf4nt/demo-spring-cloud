package com.example.demo.client.spring.cloud.demoapispringcloud;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@ActiveProfiles("test-profile")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class ApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void api() throws Exception {
        ResultActions perform = mockMvc.perform(get("/api"));

        MockHttpServletResponse response = perform.andReturn().getResponse();
        assertThat(response.getStatus(), is(200));
        assertThat(response.getContentAsString(), notNullValue());
    }

    @Test
    public void root() throws Exception {
        ResultActions perform = mockMvc.perform(get("/"));

        MockHttpServletResponse response = perform.andReturn().getResponse();
        assertThat(response.getStatus(), is(200));
        assertThat(response.getContentAsString(), notNullValue());
    }
}
