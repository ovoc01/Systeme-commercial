package org.syscom.module_achat.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "details")
@Data
public class Details {

    @Id
    @Column(name = "idDetails")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "idBesoins", nullable = false)
    private Besoin besoin;


   
    @ManyToOne
    @JoinColumn(name = "idProduits", nullable = false)
    private Produit produit;

    @ManyToOne
    @JoinColumn(name = "idEmploye", nullable = false)
    private Employe employe;

    @Column(name = "quantite")
    private Integer quantite;

    
}