package com.example.vuldetect.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
public class User {
    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String username;

    private String password;

    protected User() {

    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
