package com.btv.app.features.image;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "public_id")
    private String publicId;
    @Column(name = "secure_url")
    private String secureUrl;

    public Image(String publicId, String secureUrl) {
        this.publicId = publicId;
        this.secureUrl = secureUrl;
    }
}
