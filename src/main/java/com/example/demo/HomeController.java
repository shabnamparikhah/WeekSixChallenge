package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    UserRepository userRepository;

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
        return "login";
    }
    @RequestMapping("/")
    public String message(@ModelAttribute Message message , BindingResult result , Model model)
    {

        if(getUser() == null ) {
            model.addAttribute("message",messageRepository.findAll());
        }
        else {
            String username = getUser().getUsername();
            model.addAttribute("login", messageRepository.findByUsername(username));
            return "message";
        }
        return "message";


    }

    @RequestMapping(value = "/addmessage" , method = RequestMethod.GET)
    public String addmessage(Model model)

    {
        model.addAttribute("message" , new Message());
        return "addmessage";
    }
    @PostMapping("/processmessage")
    public String processFormmessage(@ModelAttribute Message message , BindingResult result , Model model)
    {
        if(result.hasErrors())
        {
            return "fullmessagepost";
        }
        String username = getUser().getUsername();
        message.setUsername(username);
        messageRepository.save(message);
        model.addAttribute("message", messageRepository.findByUsername(username));
        return "fullmessage";

    }



    @RequestMapping(value = "/fullmessage" , method = RequestMethod.GET)
    public String fullmessage(Model model)

    {
        String username = getUser().getUsername();
        model.addAttribute("message", messageRepository.findByUsername(username));
        return "fullmessage";
    }
    @RequestMapping(value = "/fullmessagepost" , method = RequestMethod.POST)
    public String processfullFormessage(@Valid Message message , BindingResult result)
    {
        if(result.hasErrors())
        {
            return "message";
        }

        messageRepository.save(message);
        return "fullmessage";
    }

    @GetMapping("/login")
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

    @RequestMapping("/update/{id}")
    public String updatemessage(@PathVariable("id") long id , Model model)
    {
        model.addAttribute("message",messageRepository.findById(id).get());
        return  "addmessage";
    }
    @RequestMapping("/delete/{id}")
    public String delmessage(@PathVariable("id") long id )
    {
        messageRepository.deleteById(id);
        return "fullmessage";
    }
    private User getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentusername = authentication.getName();
        User user = userRepository.findByUsername(currentusername);
        if(user== null)
        {
            return null;
        }
        return user;
    }

}
