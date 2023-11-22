package org.syscom.module_achat.data.entity;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Locale;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Proforma {
    @Id
    @Column(name = "idProforma")
    private int id;

    @ManyToOne
    @JoinColumn(name = "idFournisseurs", nullable = false)
    private Fournisseur fournisseur;

    @Column(name = "dateCreation")
    private LocalDate dateCreation;

    public void setDateCreation(LocalDate localDate) {
        if (localDate == null) {
            this.dateCreation = LocalDate.now();
        }
        this.dateCreation = localDate;
    }
    public String generateReference() {
       

        return "PRF-" + dateCreation.toString()+ "-FRN" + this.fournisseur.getId() + "-DMD" + this.id;
    }

    public String formatTimestamp() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy 'à' HH:mm", Locale.ENGLISH);
        return dateFormat.format(dateCreation);
    }


}
