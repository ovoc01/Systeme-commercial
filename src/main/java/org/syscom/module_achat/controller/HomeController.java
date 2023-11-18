package org.syscom.module_achat.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.syscom.module_achat.data.entity.Employe;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/home")
public class HomeController {
    
    
    public Employe getEmploye(){
        return new Employe();
    }
    @GetMapping
    public String index(Model model,HttpSession session){
        model.addAttribute("employe", session.getAttribute("employe"));
        System.out.println("employe: " + session.getAttribute("employe"));
        return "pages/home/index";
    }
}
