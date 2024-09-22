package com.dailycodework.dream_shops.service.product;

import com.dailycodework.dream_shops.dto.ImageDto;
import com.dailycodework.dream_shops.dto.ProductDto;
import com.dailycodework.dream_shops.exceptions.AlreadyExistException;
import com.dailycodework.dream_shops.exceptions.ResourceNotFounException;
import com.dailycodework.dream_shops.model.Category;
import com.dailycodework.dream_shops.model.Image;
import com.dailycodework.dream_shops.model.Product;
import com.dailycodework.dream_shops.repository.CategoryRepository;
import com.dailycodework.dream_shops.repository.ImageRepository;
import com.dailycodework.dream_shops.repository.ProductRepository;
import com.dailycodework.dream_shops.request.AddProductRequest;
import com.dailycodework.dream_shops.request.ProductUpdateRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService implements IProductService {
   private final ProductRepository productRepository;
   private final CategoryRepository categoryRepository;
   private final ImageRepository imageRepository;
   private final ModelMapper modelMapper;
    @Override
    public Product addProduct(AddProductRequest product) {
        // chekc if the category is found in the DB  if yes than set it as new product category

        if(productExists(product.getName(), product.getBrand())){
            throw new AlreadyExistException(product.getBrand() + " " + product.getName() + " already exists,please update product");
        }

        Category category = Optional.ofNullable(categoryRepository.findByName(product.getCategory().getName()))
                .orElseGet(() -> {
                    Category newCategory = new Category(product.getCategory().getName());
                    return categoryRepository.save(newCategory);
                });
          product.setCategory(category);
          return productRepository.save(createProduct(product, category));
    }

    private boolean productExists(String name , String brand){
        return productRepository.existsByNameAndBrand(name, brand);
    }


  private Product createProduct(AddProductRequest product, Category category) {
       return new Product(
               product.getName(),
               product.getBrand(),
               product.getPrice(),
               product.getInventory(),
               product.getDescription(),
               category
       );
  }
    @Override
    public List<Product> getAllProducts() {
       return productRepository.findAll();
    }

    @Override
    public List<Product> getProductsByCategory(String category) {
      return productRepository.findByCategoryName(category);
    }

    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    @Override
    public List<Product> getProductByCategoryAndBrand(String category, String brand) {
        return productRepository.findByCategoryNameAndBrand(category, brand);
    }

    @Override
    public List<Product> getProductByName(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public List<Product> getProductByBrandAndName(String brand, String name) {
        return productRepository.findByBrandAndName(brand, name);
    }

    @Override
    public Long countProductsByBrandAndName(String brand, String name) {
        return productRepository.countByBrandAndName(brand, name);
    }

    @Override
     public Product getProductById(Long id) {
       return productRepository.findById(id)
               .orElseThrow(() -> new ResourceNotFounException("Product not found"));
    }

    @Override
    public void deleteProductById(Long id) {
        productRepository.findById(id).ifPresentOrElse(productRepository::delete, () -> {
            throw new ResourceNotFounException("Product not found");
        });
    }

    @Override
    public Product updateProduct(ProductUpdateRequest product, Long productId) {
             return productRepository.findById(productId)
                     .map(existingProduct -> updateExistingProduct(existingProduct, product))
                     .map(productRepository::save)
                     .orElseThrow(() -> new ResourceNotFounException("Product not found"));
    }
    // a helper method
    private Product updateExistingProduct(Product existingProduct , ProductUpdateRequest request){
        existingProduct.setName(request.getName());
        existingProduct.setBrand(request.getBrand());
        existingProduct.setPrice(request.getPrice());
        existingProduct.setInventory(request.getInventory());
        existingProduct.setDescription(request.getDescription());
        // update category also
        Category category = categoryRepository.findByName(request.getCategory().getName());
        existingProduct.setCategory(category);
        return existingProduct;
    }
@Override
public List<ProductDto>getConvertedProducts(List<Product> products){
        return products.stream().map(this::convertToDto).toList();
    }

 @Override
 public ProductDto convertToDto(Product product){
        ProductDto productDto = modelMapper.map(product ,ProductDto.class);
        List<Image>images = imageRepository.findByProductId(product.getId());
        List<ImageDto>imageDtos = images.stream().map(image -> modelMapper.map(image,ImageDto.class))
                .toList();
        productDto.setImages(imageDtos);
        return productDto;

    }
}
