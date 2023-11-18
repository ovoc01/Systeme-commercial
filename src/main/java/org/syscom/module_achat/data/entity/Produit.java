package org.syscom.module_achat.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "produits")
@Data
public class Produit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idProduits")
    private int id;

    @Column(name = "libelle")
    private String libelle;

    @ManyToOne
    @JoinColumn(name = "idNature", nullable = false)
    private Nature nature;

    // getters and setters
}