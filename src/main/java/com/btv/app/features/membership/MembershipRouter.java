package com.btv.app.features.membership;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/membership")
public class MembershipRouter {
    private final MembershipController membershipController;
    @Autowired
    public MembershipRouter(MembershipController membershipController) {
        this.membershipController = membershipController;
    }
    @GetMapping("/getAllMemberships")
    public List<Membership> getAllMemberships(){
        return membershipController.getAllMemberships();
    }

    @GetMapping("/getMembershipByID/{id}")
    public Membership getMembershipByID(@PathVariable("id") Long id){
        return membershipController.getMembershipByID(id);
    }
}
