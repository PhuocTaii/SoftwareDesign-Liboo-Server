package com.btv.app.features.membership.services;

import com.btv.app.features.membership.model.Membership;
import com.btv.app.features.user.models.User;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class MembershipService {
    private final MembershipRepository membershipRepository;
    public List<Membership> getAllMemberships() {
        return membershipRepository.findAll();
    }

    public Membership getMembershipByID(Long id){
        Optional<Membership> optionalMembership = membershipRepository.findById(id);
        return optionalMembership.orElse(null);
    }

    public Membership addMembership(Membership membership){
        if(membershipRepository.existsByType(membership.getType())){
            throw new DataIntegrityViolationException("Membership already exists");
        }
        return membershipRepository.save(membership);
    }

    public Membership modifyMembership(Membership curMem, Membership updateMem){
        if(updateMem.getMembershipFee() != null){
            curMem.setMembershipFee(updateMem.getMembershipFee());
        }
        if(updateMem.getReserve() != null) {
            curMem.setReserve(updateMem.getReserve());
        }
        if(updateMem.getType() != null) {
            curMem.setType(updateMem.getType());
        }
        if(updateMem.getMaxBook() != null) {
            curMem.setMaxBook(updateMem.getMaxBook());
        }
        if(updateMem.getMaxRenewal() != null) {
            curMem.setMaxRenewal(updateMem.getMaxRenewal());
        }
        return membershipRepository.save(curMem);
    }

    public void deleteMembership(Long id){
        membershipRepository.deleteById(id);
    }

    public Membership getMembershipByType(String type){
        return membershipRepository.findByType(type);
    }

    public List<Integer> getMembershipAmount(List<User> users){
        List<Integer> res = new ArrayList<>(4);
        for(int i = 0; i < 4; i++){
            res.add(0);
        }
        for(User u:users){
            if(u.getMembership() != null){
                switch (u.getMembership().getType()){
                    case "Free":
                        res.set(0, res.get(0) + 1);
                        break;
                    case "Regular":
                        res.set(1, res.get(1) + 1);
                        break;
                    case "Premium":
                        res.set(2, res.get(2) + 1);
                        break;
                    case "Student":
                        res.set(3, res.get(3) + 1);
                        break;
                }
            }
        }

        return res;
    }
}
