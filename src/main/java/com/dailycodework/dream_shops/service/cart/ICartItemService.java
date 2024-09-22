package com.dailycodework.dream_shops.service.cart;

import com.dailycodework.dream_shops.model.Cart;

import java.math.BigDecimal;

public interface ICartItemService {

    // add item to the cart
    void addItemToCart(Long cartId, Long productId, int quantity);
    void removeItemFromCart(Long cartId , Long productId);
    void updateItemQuantity(Long cartId, Long productId, int quantity);

}
