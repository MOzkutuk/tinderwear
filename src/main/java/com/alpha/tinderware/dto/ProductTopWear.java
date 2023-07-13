package com.alpha.tinderware.dto;

import com.alpha.tinderware.entity.MatchParameters;
import com.alpha.tinderware.entity.Product;
import com.alpha.tinderware.entity.TopWear;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductTopWear {

    private Product product;
    private TopWear topWear;
    private MatchParameters matchParameters;



}
