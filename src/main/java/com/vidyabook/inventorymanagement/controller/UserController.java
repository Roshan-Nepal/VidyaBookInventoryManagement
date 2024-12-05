package com.vidyabook.inventorymanagement.controller;

import com.vidyabook.inventorymanagement.dto.homestat.HomeStatDto;
import com.vidyabook.inventorymanagement.dto.book.BookRequestDto;
import com.vidyabook.inventorymanagement.dto.login.UserLoginRequestDto;
import com.vidyabook.inventorymanagement.dto.login.UserLoginResponseDto;
import com.vidyabook.inventorymanagement.entity.User;
import com.vidyabook.inventorymanagement.service.BookServiceImplementation;
import com.vidyabook.inventorymanagement.service.UserServiceImplementation;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
public class UserController {

    private final UserServiceImplementation userServiceImplementation;
    private final BookServiceImplementation bookServiceImplementation;
    public UserController(UserServiceImplementation userServiceImplementation,BookServiceImplementation bookServiceImplementation) {
        this.userServiceImplementation = userServiceImplementation;
        this.bookServiceImplementation = bookServiceImplementation;
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
    public String userLogin(@ModelAttribute("login") UserLoginRequestDto userLoginRequestDto, Model model, RedirectAttributes redirectAttributes, HttpSession httpSession) {
        UserLoginResponseDto userLoginResponseDto = userServiceImplementation.userLogin(userLoginRequestDto.getIdentifier(), userLoginRequestDto.getPassword(), userLoginRequestDto);
        try {
            if (userLoginResponseDto.getUserName().isBlank()) {
                redirectAttributes.addFlashAttribute("invalid",true);
//                model.addAttribute("invalid",true);
                httpSession.setAttribute("name", "");
                return "redirect:/login";
            }
            httpSession.setAttribute("checkSession",true);
            httpSession.setAttribute("username", userLoginResponseDto.getUserName());
            model.addAttribute("loggedIn", true);
//            model.addAttribute("userName", "Welcome, " + httpSession.getAttribute("username"));
            model.addAttribute("bookRequestDto", new BookRequestDto());

            return "redirect:/home";
        } catch (Exception e) {
            model.addAttribute("invalid", true);
            httpSession.invalidate();
            return "login";
        }
    }

    @GetMapping("/home")
//    public String home(RedirectAttributes redirectAttributes) {
    public String home(Model model, HttpSession httpSession, RedirectAttributes redirectAttributes) {

//        boolean message =(boolean) model.getAttribute("loggedIn");
//          boolean sessionOn = redirectAttributes.getAttribute("session").isNew();
//        try {
            String sessionOn = (String) httpSession.getAttribute("username");
            if (sessionOn == null) {
                httpSession.invalidate();
                redirectAttributes.addFlashAttribute("loginRequired",true);
                return "redirect:/login";
            }
            HomeStatDto stats = bookServiceImplementation.home();
            model.addAttribute("stats", stats);
            return "home";

//        }catch (Exception e){
//            httpSession.invalidate();
//            redirectAttributes.addFlashAttribute("loginRequired",true);
//            return "redirect:/login";
//        }
//        return "home";
//        model.addAttribute("message", "LOGIN TO CONTINUE");
//        redirectAttributes.addFlashAttribute("message", "LOGIN TO CONTINUE");

    }

    @GetMapping("/logout")
    public String logout(Model model, HttpSession httpSession, RedirectAttributes redirectAttributes){
        model.addAttribute("message","YOU HAVE BEEN LOGGET OUT");
        redirectAttributes.addFlashAttribute("logout",true);
        httpSession.invalidate();
        return "redirect:/login";
    }

//    @GetMapping("/home/register")
//    public String showRegistrationForm(Model model) {
//        model.addAttribute("user", new User());
//        return "registrationForm"; // Thymeleaf template name
//    }
//
//    @PostMapping("/home/register")
//    public String registerUser(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return "registrationForm";
//        }
//
//        user.setPassword(user.getPlainPassword());
//        userServiceImplementation.saveUser(user);
//        return "redirect:/login";
//    }
    @GetMapping("/test")
    public String home1(){
        return "fragments/test";
    }
//
}
