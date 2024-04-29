package com.btv.app.features.renewal.services;

import com.btv.app.features.membership.model.Membership;
import com.btv.app.features.renewal.model.Renewal;
import com.btv.app.features.transaction.models.Transaction;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class RenewalService {
    private final int PAGE_SIZE = 10;
    private final RenewalRepository renewalRepository;

    public Page<Renewal> getAllRenewals(int pageNumber) {
        return renewalRepository.findAll(PageRequest.of(pageNumber, PAGE_SIZE, Sort.by("id").descending()));
    }

    public Page<Renewal> getRenewalByUserID(Long id, int pageNumber) {
        return renewalRepository.findByTransaction_User_Id(id, PageRequest.of(pageNumber, PAGE_SIZE, Sort.by("id").descending()));
    }

    public Renewal requestRenewal(Renewal renewal) {
        return renewalRepository.save(renewal);
    }

    public Boolean checkIfRenewalValid(Transaction transaction) {
        // check membership
        Membership membership = transaction.getUser().getMembership();
        if(transaction.getRenewalCount() >= membership.getMaxRenewal()) {
            return false;
        }

        // check if returned
        if(transaction.getReturnDate() != null) {
            return false;
        }

        // check time
        if(!LocalDate.now().isBefore(transaction.getDueDate())) {
            return false;
        }
        return true;
    }
}
