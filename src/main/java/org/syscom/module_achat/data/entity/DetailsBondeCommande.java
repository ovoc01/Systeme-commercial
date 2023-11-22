package org.syscom.module_achat.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "details_bonde_commande")
@Getter
@Setter
public class DetailsBondeCommande {

    @Id
    @Column(name = "idDetailsBondeCommande")
    private int id;

    @ManyToOne
    @JoinColumn(name = "idBondeCommande", nullable = false)
    private BondeCommande bondeCommande;

    @ManyToOne
    @JoinColumn(name = "idProduits", nullable = false)
    private Produit produit;

    @Column(name = "prix")
    private float prix;

    @Column(name = "quantite")
    private int quantite;

    // getters and setters
}