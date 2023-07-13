package com.alpha.tinderware.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "bottomWear")
@Getter
@Setter
@ToString
public class BottomWear {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int idp;
    private String waistline;
    private String length;
    private String closureType;
    private boolean pockets;
    private String details;
    private String style;
    private String fit;
    private String material;
    private String fabric;
    private String pattern;
    private String color;

}
