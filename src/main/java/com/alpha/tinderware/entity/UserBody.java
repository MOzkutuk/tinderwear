package com.alpha.tinderware.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "userbody")
@Getter
@Setter
@ToString
public class UserBody {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int idu;
    private String colorPalette;
    private String bodyShape;
    private String height;

}
