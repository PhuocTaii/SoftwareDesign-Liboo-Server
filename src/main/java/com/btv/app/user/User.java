    package com.btv.app.user;

    import com.btv.app.membership.Membership;
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