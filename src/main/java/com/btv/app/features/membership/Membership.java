package com.btv.app.features.membership;

import jakarta.persistence.*;

@Entity
@Table(name = "membership")
public class Membership {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "type", nullable = false, unique = true)
    private String type;
    @Column(name = "membership_fee", nullable = false, length = 100)
    private Integer membershipFee;
    @Column(name = "max_book", nullable = false, length = 100)
    private Integer maxBook;
    @Column(name = "max_renewal", nullable = false, length = 100)
    private Integer maxRenewal;
    @Column(name = "reserve", nullable = false)
    private Integer reserve;
    @Column(name = "membership_duration", nullable = false)
    private Integer duration;

    public Membership() {}

    public Membership(Long id, String type, Integer membershipFee, Integer maxBook, Integer maxRenewal, Integer reserve, Integer duration) {
        this.id = id;
        this.type = type;
        this.membershipFee = membershipFee;
        this.maxBook = maxBook;
        this.maxRenewal = maxRenewal;
        this.reserve = reserve;
        this.duration = duration;
    }

    public Membership(Membership membership) {
        this.id = membership.getId();
        this.type = membership.getType();
        this.membershipFee = membership.getMembershipFee();
        this.maxBook = membership.getMaxBook();
        this.maxRenewal = membership.getMaxRenewal();
        this.reserve = membership.getReserve();
        this.duration = membership.getDuration();
    }

    public Membership(Integer type){
        if(type == 1){
            this.id = 1L;
            this.type = "Regular";
            this.membershipFee = 30;
            this.maxBook = 3;
            this.maxRenewal = 2;
            this.reserve = 2;
            this.duration = 12;
        } else if(type == 2){
            this.id = 2L;
            this.type = "Premium";
            this.membershipFee = 50;
            this.maxBook = 5;
            this.maxRenewal = 3;
            this.reserve = 3;
            this.duration = 12;
        } else if(type == 3){
            this.id = 3L;
            this.type = "Premium";
            this.membershipFee = 20;
            this.maxBook = 4;
            this.maxRenewal = 2;
            this.reserve = 1;
            this.duration = 6;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getMembershipFee() {
        return membershipFee;
    }

    public void setMembershipFee(Integer membershipFee) {
        this.membershipFee = membershipFee;
    }

    public Integer getMaxBook() {
        return maxBook;
    }

    public void setMaxBook(Integer maxBook) {
        this.maxBook = maxBook;
    }

    public Integer getMaxRenewal() {
        return maxRenewal;
    }

    public void setMaxRenewal(Integer maxRenewal) {
        this.maxRenewal = maxRenewal;
    }

    public Integer getReserve() {
        return reserve;
    }

    public void setReserve(Integer reserve) {
        this.reserve = reserve;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
