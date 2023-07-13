package com.alpha.tinderware.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "user")
@Getter
@Setter
@ToString
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int age;
    private String firstName;
    private String lastName;
    private String gender;
    private String email;
    private String password;
    private String address;
    private String country;
    private int isActive;

}
