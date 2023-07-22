package com.alpha.tinderware.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
@Getter
@Setter
@ToString(exclude = "wishListedUsers") // Exclude wishListedUsers from toString to avoid circular reference

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

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("product") // Ignore product property when serializing to JSON
    private List<WishList> wishListedUsers = new ArrayList<>();

}
