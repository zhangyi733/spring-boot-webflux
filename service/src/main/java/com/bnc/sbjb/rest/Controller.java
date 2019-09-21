package com.bnc.sbjb.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${resource.path}")
public class Controller {

    @GetMapping("/hello-error")
    public String notYetWorld() {
        throw new IllegalStateException("Hello World");
    }

    @GetMapping("/hello")
    public String helloWorld() {
        return "Hello World";
    }
}
