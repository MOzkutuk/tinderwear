package com.alpha.tinderware.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "outfits")
@Getter
@Setter
@ToString
public class Outfit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "id_products")
    private String productIds;  // Comma-separated product IDs

    @Transient  // This means JPA will not map this to any column
    public List<Integer> getProductList() {
        return Arrays.stream(productIds.split(","))
                .map(Integer::parseInt)
                .collect(Collectors.toList());
    }
}
