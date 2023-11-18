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
    @Column(name = "idBesoins")
    private Integer id;

    @Column(name = "dateCreation")
    private Timestamp dateCreation;

    @Column(name = "etat")
    private int etat;

    private Integer idEmploye;


    
}