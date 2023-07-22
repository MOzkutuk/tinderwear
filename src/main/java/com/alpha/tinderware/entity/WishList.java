package com.alpha.tinderware.entity;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "wishlist")
@Getter
@Setter
public class WishList {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "idu")
    private Integer idu;

    @Column(name = "idp")
    private Integer idp;

    // The relationship mapping
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_idu", nullable = false)
    @JsonIgnoreProperties("wishList") // Ignore wishList property when serializing to JSON

    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "product_idp", nullable = false)
    @JsonIgnoreProperties("wishListedUsers") // Ignore wishListedUsers property when serializing to JSON

    private Product product; // make this list

}
