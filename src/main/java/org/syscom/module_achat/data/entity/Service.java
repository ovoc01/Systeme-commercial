package org.syscom.module_achat.data.entity;

import jakarta.persistence.*;
import lombok.Data;
@Entity
@Table(name = "services")
@Data
public class Service {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idServices")
    private int id;

    private String libelle;

    // getters and setters
}
