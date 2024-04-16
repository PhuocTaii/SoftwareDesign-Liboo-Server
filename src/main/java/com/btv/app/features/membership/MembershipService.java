package com.btv.app.features.membership;

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
}
