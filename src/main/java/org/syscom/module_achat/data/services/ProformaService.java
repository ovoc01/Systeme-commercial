package org.syscom.module_achat.data.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.syscom.module_achat.data.entity.DetailsProforma;
import org.syscom.module_achat.data.entity.Fournisseur;
import org.syscom.module_achat.data.entity.Proforma;
import org.syscom.module_achat.data.repository.DetailsProformaRepository;
import org.syscom.module_achat.data.repository.FournisseurRepository;
import org.syscom.module_achat.data.repository.ProformaRepository;

import jakarta.transaction.Transactional;

@Service
public class ProformaService {
    @Autowired
    private DetailsProformaRepository detailsProformaRepository;
    
    @Autowired
    private ProformaRepository proformaRepository;
    @Autowired
    private FournisseurRepository fournisseurRepository;

    @Transactional
    public void saveProforma(Proforma proforma,Integer idFournisseur, ArrayList<DetailsProforma> detailsProforma) {
       Fournisseur fournisseur =  fournisseurRepository.findById(idFournisseur.intValue()).get();
        
       proforma.setFournisseur(fournisseur);
        System.out.println("id proforma :"+proforma.getId());
        proformaRepository.save(proforma);
        for (DetailsProforma detail : detailsProforma) {
            detail.setProforma(proforma);
            detail.setId(detailsProformaRepository.getLastId());
            detailsProformaRepository.save(detail);
        }
    }
    
}
