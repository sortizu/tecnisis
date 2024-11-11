package com.tecnisis.api.tecnisis.domain.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class AppUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user")
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToOne(mappedBy = "appUser", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Person person;

    public AppUser(Long id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.person = null;
    }
}
