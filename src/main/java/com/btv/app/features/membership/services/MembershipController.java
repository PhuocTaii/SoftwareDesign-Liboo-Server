package com.btv.app.features.membership.services;

import com.btv.app.features.membership.model.Membership;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin")
@AllArgsConstructor
public class MembershipController {
    private final MembershipService membershipService;
    @GetMapping("/getAllMemberships")
    public ResponseEntity<List<Membership>> getAllMemberships(){
        try {
            List<Membership> res =  membershipService.getAllMemberships();
            return ResponseEntity.status(200).body(res);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/getMembershipByID/{id}")
    public ResponseEntity<Membership> getMembershipByID(@PathVariable("id") Long id){
        try{
            Membership res = membershipService.getMembershipByID(id);
            return ResponseEntity.status(200).body(res);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PostMapping("/addMembership")
    public ResponseEntity<Membership> addMembership(@ModelAttribute Membership membership) {
        try{
            Membership res = membershipService.addMembership(membership);
            return ResponseEntity.status(200).body(res);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @PutMapping("/modifyMembership/{id}")
    public ResponseEntity<Membership> modifyMembership(@PathVariable("id") Long id, Membership membership) {
        try{
            Membership curMem = membershipService.getMembershipByID(id);
            if(curMem == null){
                return ResponseEntity.status(404).build();
            }
            Membership res = membershipService.modifyMembership(curMem, membership);
            return ResponseEntity.status(200).body(res);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @DeleteMapping("/deleteMembership/{id}")
    public ResponseEntity<Membership> deleteMembership(@PathVariable("id") Long id) {
        try{
            Membership curMem = membershipService.getMembershipByID(id);
            if(curMem == null){
                return ResponseEntity.status(404).build();
            }
            membershipService.deleteMembership(id);
            return ResponseEntity.status(200).body(curMem);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }
}
