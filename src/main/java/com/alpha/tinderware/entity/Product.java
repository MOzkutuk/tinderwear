package com.alpha.tinderware.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "product")
@Getter
@Setter
@ToString
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String category;
    private String articleType;
    private String style;
    private String fit;
    private String material;
    private String color;
    private Double price;
    private Double discount;
    private Double rating;
    private String occasion;
    private String size;
    private String imagePath;
    private String link;
    private String gender;


}
