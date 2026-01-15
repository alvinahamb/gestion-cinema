package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home/dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "home/dashboard";
    }

    @GetMapping("/parametre")
    public String parametre() {
        return "home/parametre";
    }
}
