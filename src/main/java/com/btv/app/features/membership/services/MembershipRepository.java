package com.btv.app.features.membership.services;

import com.btv.app.features.membership.model.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface MembershipRepository extends JpaRepository<Membership, Long>{
    boolean existsByType(String type);
}
