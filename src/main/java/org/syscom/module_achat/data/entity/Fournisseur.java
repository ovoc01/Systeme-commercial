package org.syscom.module_achat.data.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "fournisseurs")
@Getter
@Setter
public class Fournisseur {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idFournisseurs")
    private int id;

    @Column(name = "libelle")
    private String libelle;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;
}
