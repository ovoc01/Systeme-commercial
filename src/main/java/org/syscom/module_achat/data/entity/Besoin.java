package org.syscom.module_achat.data.entity;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "besoins")
@Getter
@Setter

public class Besoin {

    @Id
    @Column(name = "idBesoins")
    private Integer id;

    @Column(name = "dateCreation")
    private Timestamp dateCreation;

    @Column(name = "etat")
    private int etat;

    @ManyToOne
    @JoinColumn(name = "idEmploye")
    private Employe employe;

    @Column(name = "idService")
    private Integer idServices;
    private String reference;

    @OneToMany(mappedBy = "besoin", cascade = CascadeType.ALL)
    private List<Details> details;

    // REFERENCE like REF-20211012-SERV1-EMP1-DMD1
    public String generateReference() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
        String formattedDate = dateFormat.format(dateCreation);

        return "REF-" + formattedDate + "-SERV" + this.idServices + "-EMP" + this.employe.getId() + "-DMD" + this.id;
    }

    public String getStatusString() {
        switch (this.etat) {
            case 0:
                return "En attente de validation du chef de departement";
            case 5:
                return "En attente de validation du departement achat";
            case 10:
                return "Refusé";
            default:
                return "En cours";
        }
    }

    // Format Timestamp to "dd MMM yyyy à HH:mm" (e.g., 12 May 2017 à 17:50)
    public String formatTimestamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy 'à' HH:mm", Locale.ENGLISH);
        return dateFormat.format(dateCreation);
    }

    public ArrayList<Details> groupDetails(List<Besoin> besoins) {
        Map<Integer, Integer> produitQuantiteMap = new HashMap<>();

        // Iterate through each Besoin
        for (Besoin besoin : besoins) {
            // Iterate through details of the current Besoin
            for (Details detail : besoin.getDetails()) {
                int produitId = detail.getProduit().getId();
                int quantite = detail.getQuantite();

                // Update the total quantity for the current Produit in the map
                produitQuantiteMap.put(produitId, produitQuantiteMap.getOrDefault(produitId, 0) + quantite);
            }
        }

        ArrayList<Details> newDetails = new ArrayList<>();

        
        for (Besoin besoin : besoins) {
            for (Details detail : besoin.getDetails()) {
                int produitId = detail.getProduit().getId();
                int totalQuantite = produitQuantiteMap.get(produitId);

                // Create a new Details object with the summed quantity
                Details groupedDetail = new Details();
                groupedDetail.setProduit(detail.getProduit());
                groupedDetail.setQuantite(totalQuantite);

                if(!newDetails.contains(groupedDetail))newDetails.add(groupedDetail);
            }
        }

        return newDetails;
    }

}