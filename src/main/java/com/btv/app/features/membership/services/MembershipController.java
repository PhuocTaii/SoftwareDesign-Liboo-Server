package com.btv.app.features.membership.services;

import com.btv.app.exception.MyException;
import com.btv.app.features.membership.model.Membership;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/admin")
@AllArgsConstructor
public class MembershipController {
    private final MembershipService membershipService;
    @GetMapping("/all-memberships")
    public ResponseEntity<List<Membership>> getAllMemberships(){
        List<Membership> res =  membershipService.getAllMemberships();
        return ResponseEntity.ok(res);
    }

    @GetMapping("/membership/{id}")
    public ResponseEntity<Membership> getMembershipByID(@PathVariable("id") Long id){
        Membership res = membershipService.getMembershipByID(id);
        return ResponseEntity.ok(res);
    }

    @PostMapping("/add-membership")
    public ResponseEntity<Membership> addMembership(@ModelAttribute Membership membership) {
        if(membershipService.getMembershipByType(membership.getType()) != null) {
            throw new MyException(HttpStatus.CONFLICT, "Membership already exists");
        }

        Membership res = membershipService.addMembership(membership);
        return ResponseEntity.ok(res);
    }

    @PutMapping("/modify-membership/{id}")
    public ResponseEntity<Membership> modifyMembership(@PathVariable("id") Long id, Membership membership) {
        Membership curMem = membershipService.getMembershipByID(id);
        if(curMem == null){
            throw new MyException(HttpStatus.NOT_FOUND, "Membership not found");
        }
        Membership res = membershipService.modifyMembership(curMem, membership);
        return ResponseEntity.ok(res);
    }

    @DeleteMapping("/delete-membership/{id}")
    public ResponseEntity<Membership> deleteMembership(@PathVariable("id") Long id) {
        Membership curMem = membershipService.getMembershipByID(id);
        if(curMem == null){
            throw new MyException(HttpStatus.NOT_FOUND, "Membership not found");
        }
        membershipService.deleteMembership(id);
        return ResponseEntity.ok(curMem);
    }
}
