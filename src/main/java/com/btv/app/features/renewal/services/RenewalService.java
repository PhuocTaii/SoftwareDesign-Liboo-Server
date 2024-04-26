package com.btv.app.features.renewal.services;

import com.btv.app.features.membership.model.Membership;
import com.btv.app.features.renewal.model.Renewal;
import com.btv.app.features.transaction.models.Transaction;
import com.btv.app.features.transaction.services.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class RenewalService {
    private final RenewalRepository renewalRepository;
    private final TransactionService transactionService;

    public List<Renewal> getAllRenewals(){
        try {
            return renewalRepository.findAll();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Renewal getRenewalByID(Long id){
        return renewalRepository.findById(id).orElse(null);
    }

    public Renewal requestRenewal(Renewal renewal) {
        if(checkIfRenewalValid(renewal.getTransaction())) {
            return renewalRepository.save(renewal);
        }
        else
            return null;
    }

    public Boolean checkIfRenewalValid(Transaction transaction) {
        // check membership
        Membership membership = transaction.getUser().getMembership();
        if(transaction.getRenewalCount() >= membership.getMaxRenewal()) {
            return false;
        }
        // check time
        if(!LocalDate.now().isBefore(transaction.getDueDate())) {
            return false;
        }
        return true;
    }
}
