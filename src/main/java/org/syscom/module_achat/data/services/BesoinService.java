package org.syscom.module_achat.data.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.syscom.module_achat.data.entity.Besoin;
import org.syscom.module_achat.data.entity.Details;
import org.syscom.module_achat.data.repository.BesoinRepository;
import org.syscom.module_achat.data.repository.DetailsRepository;

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
}
