    package com.btv.app.user;

    import com.btv.app.membership.Membership;
    import com.fasterxml.jackson.annotation.JsonPropertyOrder;
    import jakarta.persistence.*;

    import java.time.LocalDate;

    @Entity
    @Inheritance(strategy = InheritanceType.SINGLE_TABLE)
    @DiscriminatorColumn(name = "dtype", discriminatorType = DiscriminatorType.STRING)
    @Table(name = "user")
    @DiscriminatorValue("user")
    public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        protected Long id;

        @Column(name = "username", nullable = false, unique = true)
        protected String username;

        @Column(name = "password", nullable = false)
        protected String password;

        @Column(name = "email", nullable = false, unique = true)
        protected String email;

        @Column(name = "name", nullable = false)
        protected String name;

        @Column(name = "address", nullable = false)
        protected String address;

        @Column(name = "phone", nullable = false, unique = true)
        protected String phone;

        @Column(name = "joined_date", nullable = false)
        protected LocalDate joinedDate;

        @Column(name = "is_admin", columnDefinition = "boolean default false")
        protected Boolean isAdmin;

        public User() {
        }

        @PrePersist
        protected void onCreate() {
            joinedDate = LocalDate.now();
        }

        @Column(name = "photo")
        protected String photo;

        @ManyToOne
        @JoinColumn(name = "membership_id")
        private Membership membership;

        public User(Long id, String username, String password, String email, String name, String address, String phone, LocalDate joinedDate, Boolean isAdmin, String photo, Membership membership) {
            this.id = id;
            this.username = username;
            this.password = password;
            this.email = email;
            this.name = name;
            this.address = address;
            this.phone = phone;
            this.joinedDate = joinedDate;
            this.isAdmin = isAdmin;
            this.photo = photo;
            this.membership = membership;
        }

        public User (User tmp){
            this.id = tmp.getId();
            this.username = tmp.getUsername();
            this.password = tmp.getPassword();
            this.email = tmp.getEmail();
            this.name = tmp.getName();
            this.address = tmp.getAddress();
            this.phone = tmp.getPhone();
            this.joinedDate = tmp.getJoinedDate();
            this.isAdmin = tmp.getAdmin();
            this.photo = tmp.getPhoto();
            this.membership = tmp.getMembership();
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
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

        public Boolean getAdmin() {
            return isAdmin;
        }

        public void setAdmin(Boolean admin) {
            isAdmin = admin;
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

    @Entity
    @DiscriminatorValue("premium")
    class PremiumUser extends User {
    }

    @Entity
    @DiscriminatorValue("student")
    class Student extends User {

    }

    @Entity
    @DiscriminatorValue("admin")
    class Admin extends User {
        Admin() {
            isAdmin = true;
        }
    }