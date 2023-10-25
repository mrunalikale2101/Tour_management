package com.tourmanagement.Models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Data
@Table(name="accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = true, columnDefinition = "TEXT", name = "access_token")
    private String accessToken;

    @Column(nullable = true, columnDefinition = "TEXT", name = "refresh_token")
    private String refreshToken;

    public Account() {
    }

    public Account(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
