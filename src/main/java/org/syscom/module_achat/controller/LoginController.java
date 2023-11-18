package org.syscom.module_achat.controller;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.syscom.module_achat.data.entity.Employe;
import org.syscom.module_achat.data.repository.EmployeRepository;
import org.syscom.module_achat.exception.EmailNotFoundException;
import org.syscom.module_achat.exception.PasswordIncorrectException;

import jakarta.servlet.http.HttpSession;



@Controller
public class LoginController {
    @Autowired
    private EmployeRepository employeRepository;
    
    @GetMapping("/")
    public String index(@RequestParam(name = "error", required = false) String error){
        return "index";
    }

    @PostMapping("/login")
    public String login(@RequestParam("email") String email, @RequestParam("password") String motDePasse,HttpSession session){
        Employe employe = null;
        try{
            employe = Employe.login(email, motDePasse, employeRepository);
        }catch(EmailNotFoundException e){
             return "redirect:/?errorEmail=" + URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8)
                + "&email=" + URLEncoder.encode(email, StandardCharsets.UTF_8)
                + "&password=" + URLEncoder.encode(motDePasse, StandardCharsets.UTF_8);
        }catch(PasswordIncorrectException e){
             return "redirect:/?errorPassword=" + URLEncoder.encode(e.getMessage(), StandardCharsets.UTF_8)
                + "&email=" + URLEncoder.encode(email, StandardCharsets.UTF_8)
                + "&password=" + URLEncoder.encode(motDePasse, StandardCharsets.UTF_8);
        }
        session.setAttribute("employe", employe);
        return "redirect:/home";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "redirect:/";
    }
}
