package com.btv.app.features.renewal.services;

import com.btv.app.features.renewal.model.Renewal;
import com.btv.app.features.transaction.services.TransactionService;
import com.btv.app.features.user.models.User;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("api")
@AllArgsConstructor
public class RenewalController {
    private final RenewalService renewalService;
    private final TransactionService transactionService;

    @GetMapping("/librarian/all-renewals")
    public ResponseEntity<List<Renewal>> getAllRenewals(){
        try {
            List<Renewal> res = renewalService.getAllRenewals();
            return ResponseEntity.status(200).body(res);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
    @GetMapping("/librarian/renewal/{id}")
    public ResponseEntity<Renewal> getRenewalByID(@PathVariable("id") Long id){
        try{
            Renewal res = renewalService.getRenewalByID(id);
            return ResponseEntity.status(200).body(res);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/user/request-renewal")
    public ResponseEntity<Renewal> requestRenewal(@ModelAttribute Renewal renewal) {
        try {
            if(renewalService.checkIfRenewalValid(renewal.getTransaction())) {
                renewalService.requestRenewal(renewal);
                transactionService.increaseRenewalCount(renewal.getTransaction());
                transactionService.extendDueDate(renewal.getTransaction());
                return ResponseEntity.status(200).body(renewal);
            }
            return ResponseEntity.status(400).build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }
}
