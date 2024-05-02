package com.btv.app.features.user.models;


import com.btv.app.features.image.Image;
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
@Getter
@Setter
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "image_id")
    private Image image;

    @Column(name = "gender", nullable = false)
    private Boolean gender;

    @Column(name = "phone", nullable = false)
    private String phone;

    @ManyToOne
    @JoinColumn(name = "membership_id")
    private Membership membership;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Role role;

    @Column(name = "expiredDate")
    private LocalDate expiredDate;

    @Column(name = "available_borrow")
    private Integer availableBorrow;

    @PrePersist
    private void onCreate() {
        joinedDate = LocalDate.now();
        role = Role.USER;
        membership = new Membership(1);
        expiredDate = null;
        availableBorrow = membership.getMaxBook();
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
