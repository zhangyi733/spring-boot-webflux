package com.bnc.sbjb.rest;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;

@ExtendWith(SpringExtension.class)
@WebMvcTest(Controller.class)
class ControllerTest {

    @Value("${resource.path}")
    private String basePath;

    @Autowired
    private MockMvc mockMvc;

    private ResultMatcher equals(String string) {
        return content().string(string);
    }

    @Test
    @DisplayName("Spring says hello world")
    void testHelloWorld() throws Exception {
        mockMvc.perform(get(basePath + "/hello")).andExpect(equals("Hello World"));
    }

    @Test
    @DisplayName("Spring handles errors gracefully")
    void testNotYetWorld() throws Exception {
        mockMvc.perform(get(basePath + "/hello-error")).andExpect(status().is5xxServerError());
    }
}
