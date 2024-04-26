package com.btv.app.features.renewal.services;

import com.btv.app.features.renewal.model.Renewal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RenewalService {
    private final RenewalRepository renewalRepository;
    @Autowired
    public RenewalService(RenewalRepository renewalRepository) {
        this.renewalRepository = renewalRepository;
    }
    public List<Renewal> getAllRenewals(){
        try {
            return renewalRepository.findAll();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public Renewal getRenewalByID(Long id){
        Optional<Renewal> optionalRenewal = renewalRepository.findById(id);
        return optionalRenewal.orElse(null);
    }

    public Renewal requestRenewal(Renewal renewal){
        try {
            return renewalRepository.save(renewal);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
