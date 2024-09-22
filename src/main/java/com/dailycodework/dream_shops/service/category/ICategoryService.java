package com.dailycodework.dream_shops.service.category;

import com.dailycodework.dream_shops.model.Category;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
public interface ICategoryService {
       Category getCategoryById(Long id);
       Category getCategoryByName(String name);
       Category addCategory(Category category);
       List<Category> getAllCategories();
       Category updateCategory(Category category, Long id);
       void deleteCategoryById(Long id);

}
