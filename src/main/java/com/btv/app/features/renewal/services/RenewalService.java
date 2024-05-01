package com.btv.app.features.renewal.services;

import com.btv.app.features.authentication.services.AuthenticationService;
import com.btv.app.features.author.services.AuthorService;
import com.btv.app.features.membership.model.Membership;
import com.btv.app.features.renewal.model.Renewal;
import com.btv.app.features.transaction.models.Transaction;
import com.btv.app.features.transaction.models.TransactionBook;
import com.btv.app.features.user.models.User;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@AllArgsConstructor
public class RenewalService {
    private final int PAGE_SIZE = 10;
    private final RenewalRepository renewalRepository;
    private final AuthenticationService authenticationService;

    public Page<Renewal> getAllRenewals(int pageNumber) {
        return renewalRepository.findAll(PageRequest.of(pageNumber, PAGE_SIZE, Sort.by("id").descending()));
    }

    public Page<Renewal> getRenewalByUserID(Long id, int pageNumber) {
        return renewalRepository.findByTransaction_User_Id(id, PageRequest.of(pageNumber, PAGE_SIZE, Sort.by("id").descending()));
    }

    public Renewal requestRenewal(Renewal renewal) {
        return renewalRepository.save(renewal);
    }

    public Boolean checkIfRenewBelongToUser(TransactionBook transactionBook) {
       // check user
        if(!Objects.equals(authenticationService.getCurrentUser().getId(), transactionBook.getTransaction().getUser().getId())) {
            return false;
        }
        return true;
    }

    public Boolean checkIfRenewalValid(TransactionBook transactionBook) {
        // check membership
        Membership membership = transactionBook.getTransaction().getUser().getMembership();
        if(transactionBook.getRenewalCount() >= membership.getMaxRenewal()) {
            return false;
        }

        // check if returned
        if(transactionBook.getReturnDate() != null) {
            return false;
        }

        // check time
        if(!LocalDate.now().isBefore(transactionBook.getDueDate())) {
            return false;
        }
        return true;
    }
}
