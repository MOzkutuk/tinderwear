package com.alpha.tinderware.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "userRating")
@Getter
@Setter
@ToString
public class UserRating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userRating_Id")
    private int id;
    private int idu;
    private int idp;
    private int rating;


}
