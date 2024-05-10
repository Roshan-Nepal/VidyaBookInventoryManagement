package com.vidyabook.inventorymanagement.controller;

import com.vidyabook.inventorymanagement.dto.login.UserLoginRequestDto;
import com.vidyabook.inventorymanagement.dto.login.UserLoginResponseDto;
import com.vidyabook.inventorymanagement.service.UserServiceImplementation;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;



@Controller
public class UserController {

    private final UserServiceImplementation userServiceImplementation;

    public UserController(UserServiceImplementation userServiceImplementation) {
        this.userServiceImplementation = userServiceImplementation;
    }

    @GetMapping(value = "/")
    public String toUserLogin(Model model) {
        model.addAttribute("login", new UserLoginRequestDto());
        model.addAttribute("message", model.getAttribute("LOGIN TO CONTINUE"));
        return "login";
    }

    @GetMapping(value = "/login")
    public String userLogin(Model model) {
        model.addAttribute("login", new UserLoginRequestDto());
        return "login";
    }

    @PostMapping(value = "/login")
    public String userLogin(@ModelAttribute("login") UserLoginRequestDto userLoginRequestDto, Model model) {
        UserLoginResponseDto userLoginResponseDto = userServiceImplementation.userLogin(userLoginRequestDto.getIdentifier(), userLoginRequestDto.getPassword(), userLoginRequestDto);
        try {
            if (userLoginResponseDto.getUserName().isBlank()) {
                model.addAttribute("message", "INVALID USERNAME OR PASSWORD");
                return "login";
            }

            model.addAttribute("welcomeMessage", "WELCOME " + userLoginResponseDto.getUserName().toUpperCase());
            return "home";
        } catch (Exception e) {
            model.addAttribute("message", "INVALID USERNAME OR PASSWORD");
            return "login";
        }
    }

    @GetMapping("/home")
//    public String home(RedirectAttributes redirectAttributes) {
    public String home(Model model) {
//        model.addAttribute("message", "LOGIN TO CONTINUE");
//        redirectAttributes.addFlashAttribute("message", "LOGIN TO CONTINUE");
        return "redirect:/";
    }

}
