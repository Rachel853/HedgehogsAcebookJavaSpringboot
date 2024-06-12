package com.makersacademy.acebook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SandpitController {
    @GetMapping("/sandpit")
    public String signup(Model model) {
        return "sandpit";
    }

}