package com.dailycodework.dream_shops.service.product;

import com.dailycodework.dream_shops.dto.ProductDto;
import com.dailycodework.dream_shops.model.Product;
import com.dailycodework.dream_shops.request.AddProductRequest;
import com.dailycodework.dream_shops.request.ProductUpdateRequest;

import java.util.List;

public interface IProductService {
    Product addProduct(AddProductRequest product);
    List<Product> getAllProducts();
    List<Product> getProductsByCategory(String category);
    List<Product> getProductsByBrand(String brand);
    List<Product>getProductByCategoryAndBrand(String category, String brand);
    List<Product>getProductByName(String name);
    List<Product>getProductByBrandAndName(String brand, String name);
    Long countProductsByBrandAndName(String brand, String name);
    Product getProductById(Long id);
    void deleteProductById(Long id);
    Product updateProduct(ProductUpdateRequest product, Long productId);


    List<ProductDto>getConvertedProducts(List<Product> products);

    ProductDto convertToDto(Product product);
}
