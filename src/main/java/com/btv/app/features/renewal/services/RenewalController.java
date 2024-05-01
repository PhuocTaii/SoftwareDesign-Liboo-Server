package com.btv.app.features.renewal.services;

import com.btv.app.features.authentication.services.AuthenticationService;
import com.btv.app.features.renewal.model.Renewal;
import com.btv.app.features.transaction.services.TransactionBookService;
import com.btv.app.features.transaction.services.TransactionService;
import com.btv.app.features.user.models.User;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("api")
@AllArgsConstructor
public class RenewalController {
    private final RenewalService renewalService;
    private final TransactionBookService transactionBookService;
    private final AuthenticationService authenticationService;

    @AllArgsConstructor
    public static class RenewalListResponse {
        public List<Renewal> renewals;
        public int pageNumber;
        public int totalPages;
        public long totalElements;
    }

    @GetMapping("/librarian/renewals/{page-number}")
    public ResponseEntity<RenewalListResponse> getRenewals(@PathVariable("page-number") int pageNumber){
        try {
            Page<Renewal> res = renewalService.getAllRenewals(pageNumber);
            return ResponseEntity.status(200).body(new RenewalListResponse(res.getContent(), res.getNumber(), res.getTotalPages(), res.getTotalElements()));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
    @GetMapping("/user/renewals/{page-number}")
    public ResponseEntity<RenewalListResponse> getRenewalsByUserID(@PathVariable("page-number") int pageNumber){
        try{
            User user = authenticationService.getCurrentUser();
            Page<Renewal> res = renewalService.getRenewalByUserID(user.getId(), pageNumber);
            return ResponseEntity.status(200).body(new RenewalListResponse(res.getContent(), res.getNumber(), res.getTotalPages(), res.getTotalElements()));
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/user/request-renewal")
    public ResponseEntity<Renewal> requestRenewal(@ModelAttribute Renewal renewal) {
        try {
            System.out.println(renewal);
            if(renewalService.checkIfRenewBelongToUser(renewal.getTransactionBook())) {
                return ResponseEntity.status(403).build();
            }
            if(renewalService.checkIfRenewalValid(renewal.getTransactionBook())) {
                renewalService.requestRenewal(renewal);
                transactionBookService.increaseRenewalCount(renewal.getTransactionBook());
                transactionBookService.extendDueDate(renewal.getTransactionBook());
                return ResponseEntity.status(200).body(renewal);
            }
            return ResponseEntity.status(400).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}
