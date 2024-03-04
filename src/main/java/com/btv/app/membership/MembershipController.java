package com.btv.app.membership;

import com.btv.app.user.User;
import com.btv.app.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MembershipController {
    private final MembershipService membershipService;

    @Autowired
    public MembershipController(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

    public List<Membership> getAllMemberships(){
        try {
            return membershipService.getAllMemberships();
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public Membership getMembershipByID(Long id){
        try{
            return membershipService.getMembershipByID(id);
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }
}
