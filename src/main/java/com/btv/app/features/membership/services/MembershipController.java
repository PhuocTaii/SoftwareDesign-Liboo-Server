package com.btv.app.features.membership.services;

import com.btv.app.features.membership.model.Membership;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/membership")
public class MembershipController {
    private final MembershipService membershipService;

    @Autowired
    public MembershipController(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

    @GetMapping("/getAllMemberships")
    public List<Membership> getAllMemberships(){
        try {
            return membershipService.getAllMemberships();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @GetMapping("/getMembershipByID/{id}")
    public Membership getMembershipByID(Long id){
        try{
            return membershipService.getMembershipByID(id);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @PostMapping("/addMembership")
    public Membership addMembership(Membership membership) {
        try{
            Membership tmp = membershipService.addMembership(membership);
            return new Membership(tmp);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @PutMapping("/modifyMembership/{id}")
    public Membership modifyMembership(long id, Membership membership) {
        try{
            Membership curMem = membershipService.getMembershipByID(id);
            Membership tmp = membershipService.modifyMembership(curMem, membership);
            return new Membership(tmp);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    @DeleteMapping("/deleteMembership/{id}")
    public void deleteMembership(long id) {
        try{
            membershipService.deleteMembership(id);
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}
