package org.syscom.module_achat.data.entity;

import java.util.ArrayList;

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

    private Integer etat;

    

    public ArrayList<Details> groupBesoinsByService(ArrayList<Details> details){
        ArrayList<Details> result = new ArrayList<Details>();
        for(Details detail : details){
            if(result.size()==0){
                result.add(detail);
            }else{
                boolean found = false;
                for(Details detailResult : result){
                    if(detailResult.getBesoin().getIdServices()==detail.getBesoin().getIdServices()){
                        detailResult.setQuantite(detailResult.getQuantite()+detail.getQuantite());
                        found = true;
                        break;
                    }
                }
                if(!found){
                    result.add(detail);
                }
            }
        }        
        return result;
    }
}