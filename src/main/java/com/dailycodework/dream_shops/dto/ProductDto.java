package com.dailycodework.dream_shops.dto;

import com.dailycodework.dream_shops.model.Category;
import com.dailycodework.dream_shops.model.Image;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigInteger;
import java.util.List;
@Data
public class ProductDto {
    private Long id;
    private String name;
    private String brand;
    private BigInteger price;
    private int inventory;
    private String description;
    private Category category;
    private List<ImageDto> images;
}
