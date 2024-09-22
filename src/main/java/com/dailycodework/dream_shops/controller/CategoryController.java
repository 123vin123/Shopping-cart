package com.dailycodework.dream_shops.controller;

import com.dailycodework.dream_shops.exceptions.AlreadyExistException;
import com.dailycodework.dream_shops.exceptions.ResourceNotFounException;
import com.dailycodework.dream_shops.model.Category;
import com.dailycodework.dream_shops.response.ApiResponse;
import com.dailycodework.dream_shops.service.category.ICategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.PrivateKey;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/categories")
public class CategoryController {
    private final ICategoryService categoryService;

    @GetMapping("/get")
    public ResponseEntity<ApiResponse>getAllCategories(){
        try {
            List<Category> categories = categoryService.getAllCategories();

            return ResponseEntity.ok(new ApiResponse("Found all categories", categories));
        } catch (Exception e) {
          return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("Something went wrong", INTERNAL_SERVER_ERROR));
        }
    }

    // AddCategory
     @PostMapping("/add")
    public ResponseEntity<ApiResponse>addCategory(@RequestBody Category name){
         try {
             Category category = categoryService.addCategory(name);
             return ResponseEntity.ok(new ApiResponse("Successfully added", category));
         } catch (AlreadyExistException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(),null));
         }

     }
     @GetMapping("/category/{id}/category")
     public ResponseEntity<ApiResponse>getCategoryById(@PathVariable  Long id){
         try {
             Category category = categoryService.getCategoryById(id);
             return ResponseEntity.ok(new ApiResponse("Found category", category));
         } catch (ResourceNotFounException e) {
              return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
         }
     }
      @GetMapping("/category/{name}/category")
     public ResponseEntity<ApiResponse>getCategoryByName(@PathVariable String name){
          try {
              Category category = categoryService.getCategoryByName(name);
              return ResponseEntity.ok(new ApiResponse("Found category", category));
          } catch (ResourceNotFounException e) {
             return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
          }

      }
      @DeleteMapping("/category/{id}/delete")
    public ResponseEntity<ApiResponse>deleteCategoryById(@PathVariable Long id){
          try {
              categoryService.deleteCategoryById(id);
              return ResponseEntity.ok(new ApiResponse("Deleted successfully", null));
          } catch (ResourceNotFounException e) {
              return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
          }

      }

      @PutMapping("/category/{id}/update")
      public ResponseEntity<ApiResponse>updateCategory(@PathVariable Long id, @RequestBody Category category){
          try {
              Category updatedCategory = categoryService.updateCategory(category, id);
              return ResponseEntity.ok(new ApiResponse("Updated successfully", updatedCategory));
          } catch (ResourceNotFounException e) {
             return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
          }

      }
}
