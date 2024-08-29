package com.jwt.jwtcapstone.controller;


import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/greet")
    public String greet(HttpServletRequest request) {

        return "Wow, you logged in. "+request.getSession().getId();

    }

}
