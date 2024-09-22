package com.dailycodework.dream_shops.controller;

import com.dailycodework.dream_shops.exceptions.ResourceNotFounException;
import com.dailycodework.dream_shops.model.Cart;
import com.dailycodework.dream_shops.response.ApiResponse;
import com.dailycodework.dream_shops.service.cart.ICartService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/carts")
public class CartController  {
    private final ICartService cartService;



    @GetMapping("/{cartId}/get-cart")
    public ResponseEntity<ApiResponse>getCart(@PathVariable Long cartId){
        try {
            Cart cart = cartService.getCart(cartId);
            return ResponseEntity.ok(new ApiResponse("Success", cart));
        } catch (ResourceNotFounException e) {
           return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
    @DeleteMapping("/{cartId}/clear")
    public ResponseEntity<ApiResponse>getTotalAmount(@PathVariable long cartId){
        try {
            cartService.clearCart(cartId);
            return ResponseEntity.ok(new ApiResponse("Cart Clear Successfully", null));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
        }
    }
  @GetMapping("/{cartId}/total-price")
    public ResponseEntity<ApiResponse>getTotalAmount(@PathVariable Long cartId){
      try {
          BigDecimal totalPrice = cartService.getTotalPrice(cartId);
          return ResponseEntity.ok(new ApiResponse("Total Price", totalPrice));
      } catch (ResourceNotFounException e) {
          return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(),null));
      }
  }
}
