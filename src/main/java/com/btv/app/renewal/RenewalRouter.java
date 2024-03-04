package com.btv.app.renewal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/renewal")
public class RenewalRouter {
    private final RenewalController renewalController;
    @Autowired
    public RenewalRouter(RenewalController renewalController) {
        this.renewalController = renewalController;
    }

    @GetMapping("/getAllRenewals")
    public List<Renewal> getAllRenewals(){
        return renewalController.getAllRenewals();
    }

    @GetMapping("/getRenewalByID/{id}")
    public Renewal getRenewalByID(@PathVariable("id") Long id){
        return renewalController.getRenewalByID(id);
    }
}
