package com.dangth.encryption.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "Home.html";
    }

    @GetMapping("/cipher")
    public String cipher() {
        return "Cipher.html";
    }
}
