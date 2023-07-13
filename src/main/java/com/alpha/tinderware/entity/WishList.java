package com.alpha.tinderware.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "wishlist")
@Getter
@Setter
@ToString
public class WishList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int idu;
    private int idp;

}
