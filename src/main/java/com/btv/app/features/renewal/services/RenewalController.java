package com.btv.app.features.renewal.services;

import com.btv.app.features.renewal.model.Renewal;
import com.btv.app.features.user.models.User;
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

    @GetMapping("/all-renewals")
    public List<Renewal> getAllRenewals(){
        try {
            return renewalService.getAllRenewals();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
    @GetMapping("/renewal/{id}")
    public Renewal getRenewalByID(@PathVariable("id") Long id){
        try{
            return renewalService.getRenewalByID(id);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }


//    @PostMapping("renewal/{userId}/")
//    public ResponseEntity<Renewal> addRenewal(){
//        try{
//
////            return ResponseEntity.status(200).body(res);
//        } catch (Exception e) {
//            return ResponseEntity.status(500).build();
//        }
//    }
}
