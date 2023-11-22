package org.syscom.module_achat.data.services;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.syscom.module_achat.data.entity.Besoin;
import org.syscom.module_achat.data.entity.BesoinTransactionLog;
import org.syscom.module_achat.data.entity.Details;
import org.syscom.module_achat.data.entity.Employe;
import org.syscom.module_achat.data.repository.BesoinRepository;
import org.syscom.module_achat.data.repository.BesoinTransactionLogRepository;
import org.syscom.module_achat.data.repository.DetailsRepository;
import org.syscom.module_achat.exception.validation.NotAuthorizedToDeleteException;

import jakarta.transaction.Transactional;

@Service
public class BesoinService {
    @Autowired
    private BesoinRepository besoinRepository;
    @Autowired 
    private DetailsRepository detailsRepository;
    @Autowired 
    BesoinTransactionLogRepository besoinTransactionLogRepository;

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
        System.out.println("id: " + id);
        Besoin besoin = besoinRepository.findById(id);
       
        if(besoin.getEmploye().getId() == employe.getId()||(employe.isMgr()&&employe.getService().getId()==besoin.getIdServices())){
            for (Details detail : besoin.getDetails()) {
                detail.setEtat(-5);
                detailsRepository.save(detail);
            }
            besoinTransactionLogRepository.save(new BesoinTransactionLog(besoin,besoin.getEtat()));
            besoinRepository.deleteById(id);
            return;
        }
        throw new NotAuthorizedToDeleteException("Vous n'avez pas le droit de supprimer ce besoin");
    }

    @Transactional
    public void mgrValidateBesoin(Integer id,Employe employe){
        Besoin besoin = besoinRepository.findById(id).get();
        if (employe.isMgr() && employe.getService().getId() == besoin.getIdServices()) {
            BesoinTransactionLog besoinTransactionLog = new BesoinTransactionLog(besoin,besoin.getEtat());
            besoinTransactionLogRepository.save(besoinTransactionLog);
            besoin.setEtat(5);
            besoin.setEmploye(employe);
            besoin.setDateCreation(Timestamp.valueOf(LocalDateTime.now()));
            besoinRepository.save(besoin);
        }
    }

    

    public ArrayList<Details> getGroupedDetails(){
        List<Besoin> besoinsByEtat = besoinRepository.findByEtat(5);
        return new Besoin().groupDetails(besoinsByEtat);
    }

    @Transactional
    public void dprtAchatValidate(List<Besoin> besoinsByEtat, Employe attribute) throws Exception {
        if(besoinsByEtat.size()<1) throw new Exception("Aucun besoin a validez ");
        if(attribute.isMgr()&&attribute.getService().isServiceAchat()){
            for (Besoin besoin : besoinsByEtat) {
                BesoinTransactionLog besoinTransactionLog = new BesoinTransactionLog(besoin, 5);
                besoinTransactionLogRepository.save(besoinTransactionLog);
                besoin.setEmploye(attribute);
                besoin.setDateCreation(Timestamp.valueOf(LocalDateTime.now()));
                besoin.setEtat(10);
                System.out.println("etat :"+besoin.getEtat());
                besoinRepository.save(besoin);
            }
        }
        //throw new Exception("Permission insuffisante "+attribute.getService().isServiceAchat());
    }

     public ArrayList<Details> getGroupedDetailsValidateByAchat(){
        List<Besoin> besoinsByEtat = besoinRepository.findByEtat(10);
        return new Besoin().groupDetails(besoinsByEtat);
    }

    


}
