package com.alpha.tinderware.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "onePiece")
@Getter
@Setter
@ToString
public class OnePiece {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private Integer idp;
    private String necklineType;
    private String sleeveLength;
    private String sleeveType;
    private String waistLine;
    private String length;
    private String hemShaped;
    private String details;
    private String style;
    private String fit;
    private String material;
    private String fabric;
    private String pattern;
    private String color;

}
