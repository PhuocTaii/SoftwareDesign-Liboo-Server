package com.btv.app.renewal;

import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RenewalController {
    private final RenewalService renewalService;

    public RenewalController(RenewalService renewalService) {
        this.renewalService = renewalService;
    }

    public List<Renewal> getAllRenewals(){
        try {
            return renewalService.getAllRenewals();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public Renewal getRenewalByID(Long id){
        try{
            return renewalService.getRenewalByID(id);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
