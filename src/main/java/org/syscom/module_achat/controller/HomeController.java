package org.syscom.module_achat.controller;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
import org.syscom.module_achat.exception.validation.NotAuthorizedToDeleteException;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/home")
public class HomeController {

    @Autowired
    private ProduitRepository produitRepository;
    @Autowired
    BesoinRepository besoinRepository;
    @Autowired
    DetailsRepository detailsRepository;
    @Autowired
    BesoinService besoinService;

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

            besoin.setEtat(0);
            besoin.setEmploye(((Employe) session.getAttribute("employe")));
            besoin.setIdServices(((Employe) session.getAttribute("employe")).getService().getId());
            // System.out.println("besoin: " + besoin);
        } else {
            besoin = (Besoin) session.getAttribute("besoin");
        }

        ArrayList<Details> details = session.getAttribute("details") == null ? new ArrayList<Details>()
                : (ArrayList<Details>) session.getAttribute("details");

        session.setAttribute("besoin", besoin);
        session.setAttribute("details", details);
        model.addAttribute("employe", session.getAttribute("employe"));
        model.addAttribute("produits", this.produitsfindAll);
        model.addAttribute("pages", "create-besoin");
        System.out.println("pages: " + model.getAttribute("pages"));
        return "pages/home/besoin";
    }

    @PostMapping("add-details")
    public String addDetails(@RequestParam("produit") int idProduit, @RequestParam("quantite") int quantite,
            HttpSession session, RedirectAttributes redirectAttributes) {

        Details details = new Details();

        // details.setIdProduit(idProduit);
        details.setProduit(this.produitsfindAll.stream().filter(p -> p.getId() == idProduit).findFirst().orElse(null));
        details.setQuantite(quantite);
        details.setEmploye((Employe) session.getAttribute("employe"));

        ArrayList<Details> currentDetails = (ArrayList<Details>) session.getAttribute("details");

        // Check if the product with the same ID is already in the list
        Details existingDetails = currentDetails.stream()
                .filter(d -> d.getProduit().getId() == (idProduit))
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
        besoin.setId(besoinRepository.getLastId());
        besoin.setDateCreation(Timestamp.valueOf(LocalDateTime.now()));
        ArrayList<Details> details = (ArrayList<Details>) session.getAttribute("details");

        besoinService.saveBesoin(details, besoin);

        session.removeAttribute("besoin");
        session.removeAttribute("details");

        return "redirect:/home/besoins";
    }

    @GetMapping("cancel-besoin")
    public String cancelBesoin(HttpSession session, RedirectAttributes redirectAttributes) {
        session.removeAttribute("besoin");
        session.removeAttribute("details");
        return "redirect:/home/create-besoin";
    }

    @GetMapping("besoins")
    public String besoins(Model model, HttpSession session) {
        model.addAttribute("employe", session.getAttribute("employe"));
        model.addAttribute("besoins", besoinRepository.findAll());
        model.addAttribute("pages", "besoins");
        model.addAttribute("listBesoinByService",
                besoinRepository.findByIdServices(((Employe) session.getAttribute("employe")).getService().getId()));
        System.out.println("pages: " + model.getAttribute("pages"));
        return "pages/home/besoin";
    }

    @GetMapping("besoin/{id}/details")
    public String getBesoinDetails(@PathVariable("id") Integer id, Model model, HttpSession session) {
        Optional<Besoin> optionalBesoin = besoinRepository.findById(id);

        if (optionalBesoin.isPresent()) {
            Besoin besoin = optionalBesoin.get();
            System.out.println("makato");
            model.addAttribute("employe", session.getAttribute("employe"));
            model.addAttribute("besoinChoosed", besoin);
            model.addAttribute("details", besoin.getDetails());
            model.addAttribute("pages", "besoin-details");
            return "pages/home/besoin"; // Change the view name as needed
        } else {
            // Handle the case where the Besoin with the given ID is not found
            return "redirect:/home"; // Redirect to the list of besoins
        }
    }

    @GetMapping("besoin/{id}/delete")
    public String deleteBesoin(@PathVariable("id") Integer id, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {
        Employe employe = (Employe) session.getAttribute("employe");

        try {
            besoinService.deleteBesoin(3, employe);
            System.err.println("besoin supprimé");
        } catch (NotAuthorizedToDeleteException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/home/besoins";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Une erreur s'est produite");
            return "redirect:/home/besoins";
        }
        return "redirect:/home/besoins";
    }

    @GetMapping("besoin/details/{id}/delete")
    public String deleteDetails(@PathVariable("id") int id, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {
        Employe employe = (Employe) session.getAttribute("employe");
        ArrayList<Details> details = (ArrayList<Details>) session.getAttribute("details");
        
        System.out.println("details: " + details.size());
        details.remove(id-1);
        System.out.println("details: " + details.size());
        
        redirectAttributes.addFlashAttribute("currentDetails", details);
        return "redirect:/home/create-besoin";
    }
    @GetMapping("besoin/details/{id}/update")
    public String updateDetails(@PathVariable("id") int id, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {
        Employe employe = (Employe) session.getAttribute("employe");
        ArrayList<Details> details = (ArrayList<Details>) session.getAttribute("details");
        
        System.out.println("details: " + details.size());
        
        System.out.println("details: " + details.size());
        
        redirectAttributes.addFlashAttribute("currentDetails", details);

        redirectAttributes.addFlashAttribute("detailsUpdate", details.get(id-1));
        redirectAttributes.addFlashAttribute("idUpdate", id);
        return "redirect:/home/create-besoin";
    }    

    @PostMapping("update-details")
    public String updateDetails(@RequestParam(name = "id",required = false) Integer id,@RequestParam("idUpdate")int idUpdate,@RequestParam("quantite") int quantite, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {
        Employe employe = (Employe) session.getAttribute("employe");
        ArrayList<Details> details = (ArrayList<Details>) session.getAttribute("details");
        
        System.out.println("details: " + details.size());
        details.get(id-1).setQuantite(quantite);
        System.out.println("details: " + details.size());
        
        redirectAttributes.addFlashAttribute("currentDetails", details);
        return "redirect:/home/create-besoin";
    }

}
