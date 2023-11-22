package org.syscom.module_achat.data.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "bonde_commande")
@Getter
@Setter
public class BondeCommande {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idBondeCommande")
    private int id;

    @ManyToOne
    @JoinColumn(name = "idFournisseurs", nullable = false)
    private Fournisseur fournisseur;

    @Column(name = "dateCreation")
    private LocalDate dateCreation;
  
}
