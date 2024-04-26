package com.btv.app.features.membership.services;

import com.btv.app.features.membership.model.Membership;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

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
}
