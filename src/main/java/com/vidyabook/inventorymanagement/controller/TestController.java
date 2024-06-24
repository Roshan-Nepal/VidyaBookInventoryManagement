package com.vidyabook.inventorymanagement.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class TestController {
    @GetMapping("/header")
    public String homeReturn(Model model){
        model.addAttribute("username","roshan");
        return "fragments/header";
    }
}
