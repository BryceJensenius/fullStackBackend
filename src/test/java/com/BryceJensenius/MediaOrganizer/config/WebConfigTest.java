package com.BryceJensenius.MediaOrganizer.config;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.options;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;

import com.BryceJensenius.MediaOrganizer.controller.UserController;
import com.BryceJensenius.MediaOrganizer.service.UserService;

@WebMvcTest(UserController.class)
@Import(WebConfig.class)
class WebConfigTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void preflightRequestToLoginAllowsConfiguredOrigin() throws Exception {
        mockMvc.perform(options("/auth/login")
                .header("Origin", "https://mo.tradelens.space")
                .header("Access-Control-Request-Method", "POST")
                .header("Access-Control-Request-Headers", "content-type"))
            .andExpect(status().isOk())
            .andExpect(header().string("Access-Control-Allow-Origin", "https://mo.tradelens.space"))
            .andExpect(header().string("Access-Control-Allow-Credentials", "true"));
    }
}