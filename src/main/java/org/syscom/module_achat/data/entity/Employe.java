package org.syscom.module_achat.data.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "employe")
@Data
public class Employe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idEmploye")
    private int id;

    private String nom;

    private String prenom;
    private String email;

    private String motDePasse;

    @ManyToOne
    @JoinColumn(name = "idServices", nullable = false)
    private Service service;

}
