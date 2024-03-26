package com.btv.app.membership;

import com.btv.app.user.User;
import com.btv.app.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MembershipService {
    private final MembershipRepository membershipRepository;
    @Autowired
    public MembershipService(MembershipRepository membershipRepository) {
        this.membershipRepository = membershipRepository;
    }
    public List<Membership> getAllMemberships() {
        return membershipRepository.findAll();
    }

    public Membership getMembershipByID(Long id){
        Optional<Membership> optionalMembership = membershipRepository.findById(id);
        return optionalMembership.orElse(null);
    }

    public Membership addMembership(Membership membership){
        return membershipRepository.save(membership);
    }

    public Membership modifyMembership(Membership curMem, Membership updateMem){
        if(updateMem.getMembershipFee() != null){
            curMem.setMembershipFee(updateMem.getMembershipFee());
        }
        if(updateMem.getDuration() != null) {
            curMem.setDuration(updateMem.getDuration());
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
