package com.btv.app.features.membership.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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

    public Membership(Integer type){
        if(type == 0){
            this.id = 1L;
            this.type = "Free";
            this.membershipFee = 0;
            this.maxBook = 0;
            this.maxRenewal = 0;
            this.reserve = 0;
        } else if(type == 2){
            this.id = 2L;
            this.type = "Regular";
            this.membershipFee = 50000;
            this.maxBook = 3;
            this.maxRenewal = 2;
            this.reserve = 2;
        } else if(type == 3){
            this.id = 3L;
            this.type = "Premium";
            this.membershipFee = 75000;
            this.maxBook = 5;
            this.maxRenewal = 3;
            this.reserve = 3;
        } else if(type == 4){
            this.id = 4L;
            this.type = "Student";
            this.membershipFee = 30000;
            this.maxBook = 4;
            this.maxRenewal = 2;
            this.reserve = 1;
        }
    }
}
