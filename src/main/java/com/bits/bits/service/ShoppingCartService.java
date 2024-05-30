package com.bits.bits.service;

import com.bits.bits.model.CartItem;
import com.bits.bits.model.ProductModel;
import com.bits.bits.model.ShoppingCart;
import com.bits.bits.repository.CartItemRepository;
import com.bits.bits.repository.ProductRepository;
import com.bits.bits.repository.ShoppingCartRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingCartService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShoppingCartService.class);

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    private ShoppingCart shoppingCart;

    public void addProductToShoppingCart(Long productId, int quantity) {
        if (shoppingCart == null) {
            shoppingCart = new ShoppingCart();
            shoppingCartRepository.save(shoppingCart);
        }

        ProductModel product = productRepository.findById(productId).orElse(null);
        if (product != null) {
            LOGGER.info("product found: {}", product);

            boolean itemFound = false;
            for (CartItem item : shoppingCart.getItems()) {
                if (item.getProduct().equals(product)) {
                    item.setQuantity(item.getQuantity() + quantity);
                    cartItemRepository.save(item);
                    itemFound = true;
                    break;
                }
            }

            if (!itemFound) {
                CartItem newCartItem = new CartItem();
                newCartItem.setProduct(product);
                newCartItem.setQuantity(quantity);
                newCartItem.setShoppingCart(shoppingCart);
                cartItemRepository.save(newCartItem);
                shoppingCart.getItems().add(newCartItem);
                LOGGER.info("CartItem successfully added: {}", newCartItem);
            }
            shoppingCartRepository.save(shoppingCart);
        } else {
            LOGGER.error("product with id: {}, not found", productId);
        }
    }

    public void removeProductFromShoppingCart(Long shoppingCartId, Long productId, int quantity) {
        Optional<ShoppingCart> cartOptional = shoppingCartRepository.findById(shoppingCartId);
        if (cartOptional.isPresent()) {
            ShoppingCart shoppingCart = cartOptional.get();
            CartItem itemToRemove = shoppingCart.getItems().stream()
                    .filter(item -> Long.valueOf(item.getProduct().getProductId()).equals(productId))
                    .findFirst().orElse(null);
            if (itemToRemove != null) {
                if (itemToRemove.getQuantity() > quantity) {
                    itemToRemove.setQuantity(itemToRemove.getQuantity() - quantity);
                    cartItemRepository.save(itemToRemove);
                    LOGGER.info("Quantity of product with id : {} decreased by {}", productId, quantity);
                } else {
                    shoppingCart.getItems().remove(itemToRemove);
                    cartItemRepository.delete(itemToRemove);
                    LOGGER.info("Product with id: {} removed from cart", productId);
                }
                shoppingCartRepository.save(shoppingCart);
            } else {
                LOGGER.warn("Product with id: {} not found in cart", productId);
            }
        } else {
            LOGGER.error("Shopping cart with id: {} not found", shoppingCartId);
        }
    }
}
