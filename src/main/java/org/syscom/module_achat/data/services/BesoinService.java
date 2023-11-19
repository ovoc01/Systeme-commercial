package org.syscom.module_achat.data.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.syscom.module_achat.data.entity.Besoin;
import org.syscom.module_achat.data.entity.Details;
import org.syscom.module_achat.data.entity.Employe;
import org.syscom.module_achat.data.repository.BesoinRepository;
import org.syscom.module_achat.data.repository.DetailsRepository;
import org.syscom.module_achat.exception.validation.NotAuthorizedToDeleteException;

import jakarta.transaction.Transactional;

@Service
public class BesoinService {
    @Autowired
    private BesoinRepository besoinRepository;
    @Autowired 
    private DetailsRepository detailsRepository;

    @Transactional
    public void saveBesoin(ArrayList<Details> details,Besoin besoin) {
        System.out.println("besoin: " + besoin);
        besoinRepository.save(besoin);
        for (Details detail : details) {
            detail.setBesoin(besoin);
            detailsRepository.save(detail);
            System.out.println("detail: " + detail);
        }
    }

    @Transactional
    public void deleteBesoin(int id,Employe employe) {
        Besoin besoin = besoinRepository.findById(id);
        if(besoin.getEmploye().getId() == employe.getId()||(employe.isMgr()&&employe.getService().getId()==besoin.getIdServices())){
            for (Details detail : besoin.getDetails()) {
                detailsRepository.delete(detail);
            }
            besoinRepository.deleteById(id);
            return;
        }
        throw new NotAuthorizedToDeleteException("Vous n'avez pas le droit de supprimer ce besoin");
    }
}
