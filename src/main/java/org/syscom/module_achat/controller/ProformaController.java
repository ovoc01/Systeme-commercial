package org.syscom.module_achat.controller;

import java.time.LocalDate;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.syscom.module_achat.data.entity.DetailsProforma;
import org.syscom.module_achat.data.entity.Produit;
import org.syscom.module_achat.data.entity.Proforma;
import org.syscom.module_achat.data.repository.DetailsProformaRepository;
import org.syscom.module_achat.data.repository.FournisseurRepository;
import org.syscom.module_achat.data.repository.ProduitRepository;
import org.syscom.module_achat.data.repository.ProformaRepository;
import org.syscom.module_achat.data.services.ProformaService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/home/proforma")
public class ProformaController {
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

    @GetMapping
    public String index(Model model, HttpSession session) {
        model.addAttribute("employe", session.getAttribute("employe"));

        model.addAttribute("fournisseurs", fournisseurRepository.findAll());

        model.addAttribute("pages", "new-proforma");
        return "pages/home/besoin";
    }

    @PostMapping("/details")
    public String newProforma(Model model,@RequestParam("fournisseur")Integer idFournisseur,@RequestParam( name = "date",required=false) LocalDate date, HttpSession session) {
        Proforma fProforma = null;
        if (session.getAttribute("proforma") == null) {
            fProforma = new Proforma();
            session.setAttribute("proforma", fProforma);
        } else {
            fProforma = (Proforma) session.getAttribute("proforma");
        }
        ArrayList<DetailsProforma> details = session.getAttribute("detailsProforma") == null
                ? new ArrayList<DetailsProforma>()
                : (ArrayList<DetailsProforma>) session.getAttribute("detailsProforma");

        model.addAttribute("employe", session.getAttribute("employe"));
        model.addAttribute("produits", produitRepository.findAll());
        session.setAttribute("detailsProforma",details);
        model.addAttribute("pages", "proforma-details");
        session.setAttribute("idFournisseur", idFournisseur);
        session.setAttribute("dateProforma", date);
        return "pages/home/besoin";
    }

    @GetMapping("/details")
    public String proforma(Model model, HttpSession session){
        Proforma fProforma = null;
        if (session.getAttribute("proforma") == null) {
            fProforma = new Proforma();
            session.setAttribute("proforma", fProforma);
        } else {
            fProforma = (Proforma) session.getAttribute("proforma");
        }
        ArrayList<DetailsProforma> details = session.getAttribute("detailsProforma") == null
                ? new ArrayList<DetailsProforma>()
                : (ArrayList<DetailsProforma>) session.getAttribute("detailsProforma");

        model.addAttribute("employe", session.getAttribute("employe"));
        model.addAttribute("produits", produitRepository.findAll());
        session.setAttribute("detailsProforma",details);
        model.addAttribute("pages", "proforma-details");
        return "pages/home/besoin";
    }

    @PostMapping("/add-details")
    public String addDetails(@RequestParam("idProduit") Integer idProduit, @RequestParam("quantite") Integer quantite,
            @RequestParam("pu") float prixUnitaire, Model model, HttpSession session,
            RedirectAttributes redirectAttributes) {
        Produit produit = produitRepository.findById(idProduit).get();
        Proforma proforma = (Proforma) session.getAttribute("proforma");
        ArrayList<DetailsProforma> detailsProformas = (ArrayList<DetailsProforma>) session
                .getAttribute("detailsProforma");
        DetailsProforma detailsProforma = new DetailsProforma();
        detailsProforma.setPrix(prixUnitaire);
        detailsProforma.setQuantite(quantite.intValue());
        detailsProforma.setProduit(produit);
        detailsProforma.setProforma(proforma);
        DetailsProforma existingDetails = detailsProformas.stream().filter(d -> d.getProduit().getId() == (idProduit))
                .findFirst()
                .orElse(null);
                if(existingDetails!=null){
                    existingDetails.setPrix(prixUnitaire);
                }else{
                    detailsProforma.setId(detailsProformaRepository.getLastId());
                    detailsProforma.setQuantite(quantite.intValue());
                    detailsProformas.add(detailsProforma);
                }
        session.setAttribute("detailsProforma", detailsProformas);
        redirectAttributes.addFlashAttribute("currentDetailsProforma",detailsProformas);
       
        return "redirect:/home/proforma/details";
    }

    @PostMapping
    public String createProforma(Model model, HttpSession session) {

        return "redirect:/home/proforma";
    }


    @GetMapping("/validate")
    public String validatePropforma(Model model,HttpSession session){
        Proforma proforma = (Proforma) session.getAttribute("proforma");
        try{
            proforma.setId(proformaRepository.getLastId());
            
            proforma.setDateCreation((LocalDate)session.getAttribute("dateProforma"));
            //System.out.println((Integer)session.getAttribute("idFournisseur"));
            proformaService.saveProforma(proforma, (Integer)session.getAttribute("idFournisseur"), (ArrayList<DetailsProforma>)session.getAttribute("detailsProforma"));
            session.removeAttribute("proforma");
            session.removeAttribute("dateProforma");
            session.removeAttribute("idFournisseur");
            session.removeAttribute("detailsProforma");
            model.addAttribute("message", "Proforma enregistré avec succès");
            return "redirect:/home/proforma/lists";
        }catch(Exception e){
            e.printStackTrace();
            return "redirect:/home/proforma";
        }
        
    }

    @GetMapping("/lists")
    public String listProforma(Model model,HttpSession session){
        model.addAttribute("employe", session.getAttribute("employe"));
        model.addAttribute("proformas", proformaRepository.findAll());
        model.addAttribute("pages", "list-proforma");
        return "pages/home/besoin";
    }

    @GetMapping("/{id}/details")
    public String detailsProforma(@PathVariable("id") Integer id,Model model,HttpSession session){
        model.addAttribute("employe", session.getAttribute("employe"));
        model.addAttribute("proforma",proformaRepository.findById(id).get());
        System.out.println("proforma id:"+proformaRepository.findById(id).get());
        model.addAttribute("details", detailsProformaRepository.findByIdProforma(id.intValue()));
        model.addAttribute("pages", "details-proforma");
        return "pages/home/besoin";
    }

    
}
