package org.example.msauth.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.msauth.enums.Role;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String userName;
    private String password;
    @Enumerated(EnumType.STRING)
    private Role role; // Campo para rol


}