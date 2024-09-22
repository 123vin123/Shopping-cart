package com.dailycodework.dream_shops.controller;

import com.dailycodework.dream_shops.exceptions.ResourceNotFounException;
import com.dailycodework.dream_shops.model.Cart;
import com.dailycodework.dream_shops.model.User;
import com.dailycodework.dream_shops.response.ApiResponse;
import com.dailycodework.dream_shops.service.cart.ICartItemService;
import com.dailycodework.dream_shops.service.cart.ICartService;
import com.dailycodework.dream_shops.service.user.IUserService;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.prefix}/cartItems")
public class CartItemController {
    private final ICartItemService cartItemService;
    private final ICartService cartService;
    private final IUserService userService;
  @PostMapping("/add")
    public ResponseEntity<ApiResponse>addItemToCart(
                                                  @RequestParam Long productId,
                                                  @RequestParam Integer quantity){
        try {
              User user = userService.getAuthenticatedUser();
               Cart cart = cartService.initializeNewCart(user);

            cartItemService.addItemToCart(cart.getId() , productId, quantity);
            return ResponseEntity.ok(new ApiResponse("Add item Success" ,null));
        } catch (ResourceNotFounException e) {
           return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        } catch(JwtException e){
            return ResponseEntity.status(UNAUTHORIZED).body(new ApiResponse(e.getMessage(), null));
        }

    }
    @DeleteMapping("/{cartId}/item/{itemId}/remove")
    public ResponseEntity<ApiResponse>removeItemFromCart(@PathVariable Long cartId,  @PathVariable Long itemId){
       try {
           cartItemService.removeItemFromCart(cartId, itemId);
           return ResponseEntity.ok(new ApiResponse("Remove item Success" ,null));
       } catch (ResourceNotFounException e) {
          return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
       }
    }
@PutMapping("{cartId}/item/{itemId}/update")
    public ResponseEntity<ApiResponse>updateItemQuantity(@PathVariable Long cartId,@PathVariable Long  itemId,
                                                     @RequestParam Integer quantity){
       try {
           cartItemService.updateItemQuantity(cartId, itemId, quantity);
           return ResponseEntity.ok(new ApiResponse("Update item Success" ,null));
       } catch (ResourceNotFounException e) {
           return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
       }
    }
}
