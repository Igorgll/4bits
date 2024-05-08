package com.bits.bits.service;

import com.bits.bits.model.CartItem;
import com.bits.bits.model.ProductModel;
import com.bits.bits.model.ShoppingCart;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ShoppingCartService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShoppingCartService.class);

    private final ShoppingCart shoppingCart;

    private ShoppingCartService(){
        this.shoppingCart = new ShoppingCart();
    }

    public void addItemToShoppingCart(ProductModel product, int quantity) {
        for (CartItem item : shoppingCart.getItems()){
            if (item.getProduct().equals(product)) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }

            CartItem newCartItem = new CartItem();
            newCartItem.setProduct(product);
            newCartItem.setQuantity(quantity);
            shoppingCart.getItems().add(newCartItem);

            LOGGER.info("Cart Item adicionado com sucesso: {}", newCartItem);
        }
    }
}
