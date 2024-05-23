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
            LOGGER.info("product found: {}", product.toString());

            boolean itemFound = false;
            for (CartItem item : shoppingCart.getItems()) {
                if (item.getProduct().equals(product)) {
                    item.setQuantity(item.getQuantity() + quantity);
                    itemFound = true;
                    break;
                }
            }

            if (!itemFound) {
                CartItem newCartItem = new CartItem();
                newCartItem.setProduct(product);
                newCartItem.setQuantity(quantity);
                newCartItem.setShoppingCart(shoppingCart);
                shoppingCart.getItems().add(newCartItem);
                LOGGER.info("CartItem successfully added: {}", newCartItem);
            }
        } else {
            LOGGER.error("product with id: {}, not found", productId);
        }
        shoppingCartRepository.save(shoppingCart);
    }

    public void removeProductFromShoppingCart(Long shoppingCartId, Long productId){
        Optional<ShoppingCart> findProductInCart = shoppingCartRepository.findById(shoppingCartId);
        if (findProductInCart.isPresent()) {
            ShoppingCart shoppingCart = findProductInCart.get();
            List<CartItem> items = shoppingCart.getItems();
            items.removeIf(item -> Long.valueOf(item.getProduct().getProductId()).equals(productId));
            shoppingCart.setItems(items);
            shoppingCartRepository.save(shoppingCart);
        } else {
            throw new RuntimeException("Shopping cart not found");
        }
    }
}
