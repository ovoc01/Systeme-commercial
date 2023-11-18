package org.syscom.module_achat.data.entity;

import org.syscom.module_achat.data.repository.EmployeRepository;
import org.syscom.module_achat.exception.EmailNotFoundException;
import org.syscom.module_achat.exception.PasswordIncorrectException;

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

    public static Employe login(String email,String pwd,EmployeRepository employeRepository){
        Employe employe = employeRepository.findByEmail(email);
        if (employe==null) {
            throw new EmailNotFoundException("The email you type is incorrect or does not exist");
        }
        if (!employe.getMotDePasse().equals(pwd)) {
            throw new PasswordIncorrectException("The password you type is incorrect");
        }
        return employe;
    }

    public String getNomComplet(){
        return this.nom + " " + this.prenom;
    }
}
