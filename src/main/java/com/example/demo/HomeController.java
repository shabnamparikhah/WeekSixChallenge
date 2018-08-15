package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register" , method = RequestMethod.GET)
    public String showRegisterationPage(Model model)

    {
        model.addAttribute("user",new User());
        return "registration";
    }
    @RequestMapping(value = "/register" , method = RequestMethod.POST)
    public String processRegisterationPage(@Valid @ModelAttribute("user") User user, BindingResult result, Model model)

    {
        model.addAttribute("user",new User());
        if (result.hasErrors())
        {
            return "registration";
        }
        else
        {
            userService.saveUser(user);
            model.addAttribute("message","User Account Successfully Created");
        }
        return "index";
    }
    @RequestMapping("/")
    public String index()
    {
        return "index";
    }
    @RequestMapping("/login")
    public String login(){
        return "login";
    }
    @RequestMapping("/secure")
    public String secure(HttpServletRequest request, Authentication authentication, Principal principal){

        Boolean isAdmin = request.isUserInRole("ADMIN");
        Boolean isUser = request.isUserInRole("USER");
        UserDetails userDetails=(UserDetails) authentication.getPrincipal();
        String username = principal.getName();
        return "secure";
    }

}
