package org.syscom.module_achat.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.syscom.module_achat.data.repository.DetailsProformaRepository;
import org.syscom.module_achat.data.repository.FournisseurRepository;
import org.syscom.module_achat.data.repository.ProduitRepository;
import org.syscom.module_achat.data.repository.ProformaRepository;
import org.syscom.module_achat.data.services.BesoinService;
import org.syscom.module_achat.data.services.ProformaService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/home/bondecommandes")
public class BondeCommandeController {
    @Autowired
    private ProduitRepository produitRepository;
    @Autowired
    FournisseurRepository fournisseurRepository;
    @Autowired
    DetailsProformaRepository detailsProformaRepository;
    @Autowired
    ProformaService proformaService;
    @Autowired 
    ProformaRepository proformaRepository;
    @Autowired
    BesoinService besoinService;


    @GetMapping
    public String getAllBondeCommande(Model model, HttpSession session) {
        model.addAttribute("employe", session.getAttribute("employe"));
        model.addAttribute("pages", "list-bondecommande");
        model.addAttribute("details", besoinService.getGroupedDetailsValidateByAchat());
        return "pages/home/besoin";
    }

}
