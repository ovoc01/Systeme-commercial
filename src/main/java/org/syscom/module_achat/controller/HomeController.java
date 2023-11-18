package org.syscom.module_achat.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.syscom.module_achat.data.entity.Besoin;
import org.syscom.module_achat.data.entity.Details;
import org.syscom.module_achat.data.entity.Employe;
import org.syscom.module_achat.data.entity.Produit;
import org.syscom.module_achat.data.repository.BesoinRepository;
import org.syscom.module_achat.data.repository.DetailsRepository;
import org.syscom.module_achat.data.repository.ProduitRepository;
import org.syscom.module_achat.data.services.BesoinService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private ProduitRepository produitRepository;
    @Autowired BesoinRepository besoinRepository;
    @Autowired DetailsRepository detailsRepository;
    @Autowired BesoinService besoinService;

    private ArrayList<Produit> produitsfindAll;

    @GetMapping
    public String index(Model model, HttpSession session) {
        model.addAttribute("employe", session.getAttribute("employe"));
        System.out.println("employe: " + session.getAttribute("employe"));
        this.produitsfindAll = (ArrayList<Produit>) produitRepository.findAll();
        return "pages/home/index";
    }

    @GetMapping("create-besoin")
    public String createBesoin(Model model, HttpSession session, RedirectAttributes redirectAttributes) {

        Besoin besoin = null;
        if (session.getAttribute("besoin") == null) {
            besoin = new Besoin();
            besoin.setId(besoinRepository.getLastId());
            
            besoin.setDateCreation(Timestamp.valueOf(LocalDateTime.now()));
            besoin.setEtat(0);
            besoin.setIdEmploye(((Employe) session.getAttribute("employe")).getId());
            //System.out.println("besoin: " + besoin);
        } else {
            besoin = (Besoin) session.getAttribute("besoin");
        }

        ArrayList<Details> details = session.getAttribute("details") == null ? new ArrayList<Details>()
                : (ArrayList<Details>) session.getAttribute("details");

        session.setAttribute("besoin", besoin);
        session.setAttribute("details", details);
        model.addAttribute("employe", session.getAttribute("employe"));
        model.addAttribute("produits", this.produitsfindAll);

        return "pages/home/besoin";
    }

    @PostMapping("add-details")
    public String addDetails(@RequestParam("produit") int idProduit, @RequestParam("quantite") int quantite,
            HttpSession session, RedirectAttributes redirectAttributes) {

        Details details = new Details();

        //details.setIdProduit(idProduit);
        details.setProduit(this.produitsfindAll.stream().filter(p -> p.getId() == idProduit).findFirst().orElse(null));
        details.setQuantite(quantite);
        details.setEmploye((Employe) session.getAttribute("employe"));

        ArrayList<Details> currentDetails = (ArrayList<Details>) session.getAttribute("details");

        // Check if the product with the same ID is already in the list
        Details existingDetails = currentDetails.stream()
                .filter(d -> d.getProduit().getId()==(idProduit))
                .findFirst()
                .orElse(null);

        if (existingDetails != null) {
            // Update the quantity if the product is already in the list
            existingDetails.setQuantite(existingDetails.getQuantite() + quantite);
        } else {
            details.setId(detailsRepository.getLastId());
            currentDetails.add(details);
        }

        session.setAttribute("details", currentDetails);
        redirectAttributes.addFlashAttribute("currentDetails", currentDetails);
        return "redirect:/home/create-besoin";

    }

    @GetMapping("validate-besoin")
    public String validateBesoin(HttpSession session, RedirectAttributes redirectAttributes) {
        Besoin besoin = (Besoin) session.getAttribute("besoin");
        ArrayList<Details> details = (ArrayList<Details>) session.getAttribute("details");

        besoinService.saveBesoin(details,besoin);

        session.removeAttribute("besoin");
        session.removeAttribute("details");

        return "redirect:/home";
    }
}
