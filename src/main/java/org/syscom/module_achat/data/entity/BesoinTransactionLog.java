package org.syscom.module_achat.data.entity;

import java.sql.Timestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "besoin_transaction_log")
@NoArgsConstructor
public class BesoinTransactionLog {
    @Id
    @Column(name = "idBesoins")
    private Integer id;

    @Column(name = "dateCreation")
    private Timestamp dateCreation;

    @Column(name = "etat")
    private  int etat;

    @ManyToOne
    @JoinColumn(name = "idEmploye")
    private Employe employe;

    @Column(name = "idService")
    private Integer idServices;

    public BesoinTransactionLog(Besoin besoin,int etat) {
        this.setId(besoin.getId());
        this.setDateCreation(besoin.getDateCreation());
        this.etat = etat;
        this.setEmploye(besoin.getEmploye());
        this.setIdServices(besoin.getIdServices());

    }
    

}
