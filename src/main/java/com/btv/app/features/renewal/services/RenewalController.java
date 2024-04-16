package com.btv.app.features.renewal.services;

import com.btv.app.features.renewal.model.Renewal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/renewal")
public class RenewalController {
    private final RenewalService renewalService;

    public RenewalController(RenewalService renewalService) {
        this.renewalService = renewalService;
    }

    @GetMapping("/getAllRenewals")
    public List<Renewal> getAllRenewals(){
        try {
            return renewalService.getAllRenewals();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
    @GetMapping("/getRenewalByID/{id}")
    public Renewal getRenewalByID(@PathVariable("id") Long id){
        try{
            return renewalService.getRenewalByID(id);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
