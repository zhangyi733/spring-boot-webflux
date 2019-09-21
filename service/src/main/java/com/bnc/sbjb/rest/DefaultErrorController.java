package com.bnc.sbjb.rest;

import com.bnc.api.model.error.CustomError;
import javax.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DefaultErrorController implements ErrorController {

    private static final String PATH = "/error";

    @GetMapping(value = PATH)
    public ResponseEntity<CustomError> error(HttpServletResponse response) {
        HttpStatus status = HttpStatus.valueOf(response.getStatus());
        return ResponseEntity.status(response.getStatus()).body(new CustomError(status, status.getReasonPhrase()));
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}
