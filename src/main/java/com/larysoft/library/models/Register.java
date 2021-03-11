package com.larysoft.library.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.lang.NonNull;

import javax.persistence.*;

@Entity(name = "user")
//used to solve sterilization issues
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Register {

    @Id//used to specify the Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//used to auto generate table primary key
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "username", nullable = false, unique = true)
    @NonNull
    private String username;

    @Column(name = "email_address", nullable = false, unique = true)
    @NonNull
    private String emailAddress;

    @Column(name = "password", length = 250)
    @NonNull
    private String password;

    public Register() {
    }

    public Register(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
