package org.syscom.module_achat.data.entity;

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
@Table(name = "details_proforma")
@Getter
@Setter
public class DetailsProforma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idDetailsProforma")
    private int id;

    @ManyToOne
    @JoinColumn(name = "idProforma", nullable = false)
    private Proforma proforma;

    @ManyToOne
    @JoinColumn(name = "idProduits", nullable = false)
    private Produit produit;

    @Column(name = "prix")
    private float prix;

    @Column(name = "quantite")
    private int quantite;

    // getters and setters
}