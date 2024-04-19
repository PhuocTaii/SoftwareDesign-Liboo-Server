package com.btv.app.features.user.models;


import com.btv.app.features.membership.model.Membership;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "name", nullable = false, columnDefinition = "nvarchar(255)")
    private String name;

    @Column(name = "identifier", nullable = false, unique = true)
    private String identifier;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "birth_date", nullable = false)
    private LocalDate birthDate;

    @Column(name = "joined_date", nullable = false)
    private LocalDate joinedDate;

    @Column(name = "photo")
    private String photo;

    @Column(name = "gender", nullable = false)
    private Boolean gender;

    @ManyToOne
    @JoinColumn(name = "membership_id")
    private Membership membership;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @PrePersist
    private void onCreate() {
        joinedDate = LocalDate.now();
        role = Role.USER;
        membership = new Membership(1);
    }

    public User(Long id, String email, String password, String name, String identifier, LocalDate birthDate, String address, Boolean gender) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.name = name;
        this.identifier = identifier;
        this.address = address;
        this.birthDate = birthDate;
        this.gender = gender;
    }

    public User(User user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.password = user.getPassword();
        this.name = user.getName();
        this.identifier = user.getIdentifier();
        this.address = user.getAddress();
        this.birthDate = user.getBirthDate();
        this.joinedDate = user.getJoinedDate();
        this.photo = user.getPhoto();
        this.membership = user.getMembership();
        this.role = user.getRole();
        this.gender = user.getGender();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
