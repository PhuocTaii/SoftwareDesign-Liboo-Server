package com.btv.app.features.renewal.services;

import com.btv.app.features.authentication.services.AuthenticationService;
import com.btv.app.features.renewal.model.Renewal;
import com.btv.app.features.user.models.User;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RenewalService {
    private final int PAGE_SIZE = 10;
    private final RenewalRepository renewalRepository;
    private final AuthenticationService authenticationService;

    public Page<Renewal> getAllRenewals(int pageNumber) {
        return renewalRepository.findAll(PageRequest.of(pageNumber, PAGE_SIZE, Sort.by("id").descending()));
    }

    public Page<Renewal> getRenewalByUser(User user, int pageNumber) {
        return renewalRepository.findByTransactionBook_Transaction_User(user, PageRequest.of(pageNumber, PAGE_SIZE, Sort.by("id").descending()));
    }

    public Renewal requestRenewal(Renewal renewal) {
        System.out.println(renewal);
        return renewalRepository.save(renewal);
    }
}
