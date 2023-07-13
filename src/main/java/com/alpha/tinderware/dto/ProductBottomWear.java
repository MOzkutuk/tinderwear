package com.alpha.tinderware.dto;

import com.alpha.tinderware.entity.BottomWear;
import com.alpha.tinderware.entity.MatchParameters;
import com.alpha.tinderware.entity.Product;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class ProductBottomWear {

    private Product product;
    private BottomWear bottomWear;
    private MatchParameters matchParameters;

}
