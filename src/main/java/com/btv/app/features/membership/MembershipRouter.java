package com.btv.app.features.membership;

import com.btv.app.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/addMembership")
    public Membership addMembership(@ModelAttribute Membership membership){
        return membershipController.addMembership(membership);
    }

    @PutMapping("/modifyMembership/{id}")
    public Membership modifyMembership(@PathVariable("id") Long id, @ModelAttribute Membership membership){
        return membershipController.modifyMembership(id, membership);
    }

    @DeleteMapping("/deleteMembership/{id}")
    public void deleteMembership(@PathVariable("id") Long id){
        membershipController.deleteMembership(id);
    }
}
