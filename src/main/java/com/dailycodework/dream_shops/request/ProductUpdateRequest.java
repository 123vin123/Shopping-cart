package com.dailycodework.dream_shops.request;

import com.dailycodework.dream_shops.model.Category;
import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;
@Data
public class ProductUpdateRequest {

    private Long id;
    private String name;
    private String brand;
    private BigDecimal price;
    private int inventory;
    private String description;
    private Category category;

}
