package com.dailycodework.dream_shops.service.cart;

import com.dailycodework.dream_shops.exceptions.ResourceNotFounException;
import com.dailycodework.dream_shops.model.Cart;
import com.dailycodework.dream_shops.model.CartItem;
import com.dailycodework.dream_shops.model.Product;
import com.dailycodework.dream_shops.repository.CartItemRepository;
import com.dailycodework.dream_shops.repository.CartRepository;
import com.dailycodework.dream_shops.service.product.IProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashSet;

@Service
@AllArgsConstructor
public class CartItemService implements ICartItemService {
private final CartItemRepository  cartItemRepository;
private final IProductService  productService;
private final ICartService cartService;
private final CartRepository  cartRepository;
    @Override
    public void addItemToCart(Long cartId, Long productId, int quantity) {
      // 1. get the cart
        // 2. get the product
        // 3. check if the product available already
        //4. if yes then increase the quantity with the requested quantity
        //5. if no , then initiate a new CartItem entry
        Cart cart = cartService.getCart(cartId);
        if (cart.getCartItems() == null) {
            cart.setCartItems(new HashSet<>()); // Initialize if null
        }
        Product  product = productService.getProductById(productId);
        CartItem cartItem = cart.getCartItems()
                .stream().
                filter(cartItem1 -> cartItem1.getProduct().getId().equals(productId))
                .findFirst().orElse(new CartItem());
        if(cartItem.getId() == null){
            cartItem.setCart(cart);
            cartItem.setProduct(product);
            cartItem.setQuantity(quantity);
            cartItem.setUnitPrice(product.getPrice());
        }else{
            cartItem.setQuantity(cartItem.getQuantity() + quantity);
        }
        cartItem.setTotalPrice();
        cart.addItem(cartItem);
        cartItemRepository.save(cartItem);
        cartRepository.save(cart);
    }

    @Override
    public void removeItemFromCart(Long cartId, Long productId) {
       //1. find the cart
        //2.find the product to remove
        // 3. and remove it
        Cart cart = cartService.getCart(cartId);
        CartItem itemToRemove = getCartItem(cartId, productId);
        cart.removeItem(itemToRemove);
        cartRepository.save(cart);
    }

    @Override
    public void updateItemQuantity(Long cartId, Long productId, int quantity) {
       Cart cart = cartService.getCart(cartId);
       cart.getCartItems().stream().
                filter(cartItem -> cartItem.getProduct().getId().equals(productId)).findFirst().
                ifPresent(cartItem -> {
                    cartItem.setQuantity(quantity);
                    cartItem.setUnitPrice(cartItem.getProduct().getPrice());
                    cartItem.setTotalPrice();
                });
        BigDecimal totalAmount = cart.getCartItems()
                        .stream().map(CartItem :: getTotalPrice)
                        .reduce(BigDecimal.ZERO ,BigDecimal :: add);
        cart.setTotalAmount(totalAmount);
        cartRepository.save(cart);
    }

    public  CartItem getCartItem(Long cartId, Long productId){
        Cart cart = cartService.getCart(cartId);
        return cart.getCartItems().
                stream().
                filter(cartItem -> cartItem.getProduct().getId().equals(productId)).findFirst().
                orElseThrow(() -> new ResourceNotFounException("Item Not Found"));
    }
}
