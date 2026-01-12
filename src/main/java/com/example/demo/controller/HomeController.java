package com.example.demo.controller;

import io.micrometer.core.annotation.Counted;
import io.micrometer.core.annotation.Timed;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class HomeController {

    // Count how many times this endpoint is called
    @Counted(value = "hello.invocations", description = "Number of times /hello endpoint is called")
    // Measure time taken by this endpoint
    @Timed(value = "hello.execution.time", description = "Time taken to return hello response")
    @GetMapping
    public String hello(){
        return "Hello From Yogesh Jathar...";
    }
}
