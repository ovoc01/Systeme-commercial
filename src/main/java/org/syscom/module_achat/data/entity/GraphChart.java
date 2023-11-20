package org.syscom.module_achat.data.entity;

import java.util.ArrayList;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class GraphChart {
    private ArrayList<Details> details;
    public GraphChart(ArrayList<Details> details) {
        this.details = details;
    }

    public String libelle (){
        String libelle = "[";
        for (int i=0;i<details.size();i++) {
            libelle += details.get(i).getProduit().getLibelle();
            if(i<details.size()-1){
                libelle += ",";
            }
        }
        return libelle+"]";
    }

    public String datasets(){
        String datasets = "[";
        for (int i=0;i<details.size();i++) {
            datasets += details.get(i).getQuantite();
            if(i<details.size()-1){
                datasets += ",";
            }
        }
        return datasets+"]";
    }
}
