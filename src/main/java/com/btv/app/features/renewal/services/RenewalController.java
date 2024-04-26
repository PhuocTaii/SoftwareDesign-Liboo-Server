package com.btv.app.features.renewal.services;

import com.btv.app.features.renewal.model.Renewal;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api")
public class RenewalController {
    private final RenewalService renewalService;

    public RenewalController(RenewalService renewalService) {
        this.renewalService = renewalService;
    }

    @GetMapping("/librarian/getAllRenewals")
    public ResponseEntity<List<Renewal>> getAllRenewals(){
        try {
            List<Renewal> res = renewalService.getAllRenewals();
            return ResponseEntity.status(200).body(res);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
    @GetMapping("/librarian/getRenewalByID/{id}")
    public ResponseEntity<Renewal> getRenewalByID(@PathVariable("id") Long id){
        try{
            Renewal res = renewalService.getRenewalByID(id);
            return ResponseEntity.status(200).body(res);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/user/requestRenewal")
    public ResponseEntity<Renewal> requestRenewal(@RequestBody Renewal renewal){
        try {
            Renewal res = renewalService.requestRenewal(renewal);
            return ResponseEntity.status(200).body(res);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
