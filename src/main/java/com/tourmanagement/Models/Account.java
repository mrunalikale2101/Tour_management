package com.tourmanagement.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="accounts")
public class Account {
    @Id
    @Column(nullable = false, unique = true)
    private String username;

    @JsonIgnore
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
