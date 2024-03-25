package com.GVNCop.app.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

@Entity
@Table(name = "Account")
@Data

public class Account {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "email")
    private String email;
    @Column(name = "password")
    private String password;
    @Column(name = "name")
    private String name;
    @Column(name = "avatar")
    private String avatar;
    @Column(name = "phoneNumber")
    private String phoneNumber;
    @Column(name = "wallet")
    private Double wallet;

    //join table
    @Column(name = "roleId")
    private String roleId;

    @Column(name = "createAt")
    private Date createAt;



}
