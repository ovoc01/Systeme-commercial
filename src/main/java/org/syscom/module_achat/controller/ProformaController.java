package org.syscom.module_achat.controller;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.syscom.module_achat.data.entity.DetailsProforma;
import org.syscom.module_achat.data.entity.Proforma;
import org.syscom.module_achat.data.repository.FournisseurRepository;
import org.syscom.module_achat.data.repository.ProduitRepository;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/home/proforma")
public class ProformaController {
    @Autowired
    private ProduitRepository produitRepository;
    @Autowired FournisseurRepository fournisseurRepository;
    @GetMapping
    public String index(Model model, HttpSession session) {
        model.addAttribute("employe", session.getAttribute("employe"));

        model.addAttribute("produits", produitRepository.findAll());
        System.out.println(produitRepository.findAll());
        model.addAttribute("fournisseurs", fournisseurRepository.findAll());
        
        model.addAttribute("pages", "new-proforma");
        return "pages/home/besoin";
    }

    
    
    @PostMapping
    public String createProforma(Model model,HttpSession session){
        Proforma fProforma = null;
        if(session.getAttribute("proforma")==null){
            fProforma = new Proforma();
            session.setAttribute("proforma", fProforma);
        }else{
            fProforma = (Proforma) session.getAttribute("proforma");
        }
        ArrayList<DetailsProforma> details = session.getAttribute("detailsProforma") == null ? new ArrayList<DetailsProforma>()
                : (ArrayList<DetailsProforma>) session.getAttribute("detailsProforma");
        
        
        return "redirect:/home/proforma";
    }

    @GetMapping("add-details")
    public String addDetails(){
        return null;
    }
}
