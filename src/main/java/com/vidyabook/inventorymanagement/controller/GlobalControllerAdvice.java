package com.vidyabook.inventorymanagement.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

@ControllerAdvice
public class GlobalControllerAdvice {


    @ModelAttribute("userName")
    public String setUserName(HttpSession httpSession){
        return "Welcome, " + httpSession.getAttribute("username");
    }
}
