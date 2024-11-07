package com.example.msauth.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "usuario")

public class AuthUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario")
    private String userName;
    private String password;
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinTable(name = "usuario_roles", joinColumns = @JoinColumn(name = "usuario_id",
            referencedColumnName = "id_usuario"),
            inverseJoinColumns = @JoinColumn(name = "role_id",
             referencedColumnName = "id_role"))
    private List<Roles> roles = new ArrayList<>();

}
