package com.btv.app.author;

import jakarta.persistence.*;

@Entity
@Table(name = "author")
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;
    public Author() {}

    public Author(Author tmp) {
        this.id = tmp.getID();
        this.name = tmp.getName();
    }

    public long getID() {return this.id;}
    public String getName() {return this.name;}

    void setId(long ID) {this.id = ID;}
    void setName(String NAME) {this.name = NAME;}
}
