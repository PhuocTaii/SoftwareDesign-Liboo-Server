package com.btv.app.features.user.models;


import com.btv.app.features.membership.Membership;
import com.google.cloud.firestore.annotation.DocumentId;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class User {
    @Id
    @DocumentId
    private String id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "phone", nullable = false, unique = true)
    private String phone;

    @Column(name = "joined_date", nullable = false)
    private LocalDate joinedDate;

    @Column(name = "photo")
    private String photo;

    @ManyToOne
    @JoinColumn(name = "membership_id")
    private Membership membership;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false, columnDefinition = "varchar(255) default 'USER'")
    private Role role;

    @PrePersist
    private void onCreate() {
        joinedDate = LocalDate.now();
    }

    public User(String id, String email, String name, String address, String phone, LocalDate joinedDate, Role role, String photo, Membership membership) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.joinedDate = joinedDate;
        this.role = role;
        this.photo = photo;
        this.membership = membership;
    }

    public User (User tmp){
        this.id = tmp.getId();
        this.email = tmp.getEmail();
        this.name = tmp.getName();
        this.address = tmp.getAddress();
        this.phone = tmp.getPhone();
        this.joinedDate = tmp.getJoinedDate();
        this.role = tmp.getRole();
        this.photo = tmp.getPhoto();
        this.membership = tmp.getMembership();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDate getJoinedDate() {
        return joinedDate;
    }

    public void setJoinedDate(LocalDate joinedDate) {
        this.joinedDate = joinedDate;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Membership getMembership() {
        return membership;
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
    }

}
