package com.btv.app.features.author.model;

import com.btv.app.features.membership.model.Membership;
import com.btv.app.features.user.models.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true, columnDefinition = "nvarchar(255)")
    private String name;

    public Author(Author tmp) {
        this.id = tmp.getId();
        this.name = tmp.getName();
    }
}
