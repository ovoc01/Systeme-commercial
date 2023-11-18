package org.syscom.module_achat.data.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "besoins")
@Data
public class Besoin {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idBesoins")
    private int id;

    @Column(name = "dateCreation")
    private Timestamp dateCreation;

    @Column(name = "etat")
    private int etat;

    // getters and setters
}