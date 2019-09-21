package com.bnc.sbjb.rest;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
class DefaultErrorControllerTest {

    @Autowired
    private TestRestTemplate template;

    @Autowired
    private DefaultErrorController defaultErrorController;

    @Test
    void error() {
        ResponseEntity<String> actual = template.getForEntity("/invalidurl", String.class);
        assertThat(actual.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    }

    @Test
    void error_path() {
        assertThat(defaultErrorController.getErrorPath()).isEqualTo("/error");
    }
}
