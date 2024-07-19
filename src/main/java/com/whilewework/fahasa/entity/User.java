package com.whilewework.fahasa.entity;

import com.whilewework.fahasa.enums.UserRole;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private String password;

    private String name;

    private UserRole role;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private String img;

}