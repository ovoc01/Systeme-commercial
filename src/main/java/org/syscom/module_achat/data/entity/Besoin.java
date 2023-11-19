package org.syscom.module_achat.data.entity;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "besoins")
@Data
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
}