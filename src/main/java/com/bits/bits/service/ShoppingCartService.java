package com.bits.bits.service;

import com.bits.bits.model.CartItem;
import com.bits.bits.model.ProductModel;
import com.bits.bits.model.ShoppingCart;
import com.bits.bits.repository.CartItemRepository;
import com.bits.bits.repository.ProductRepository;
import com.bits.bits.repository.ShoppingCartRepository;
import jakarta.transaction.Transactional;
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

    @Transactional
    public void addProductToShoppingCart(Long productId, int quantity) {
        // Carregar o carrinho de compras existente ou criar um novo se não existir
        Optional<ShoppingCart> optionalShoppingCart = shoppingCartRepository.findById(1L); // Supondo que o ID do carrinho é 1
        if (optionalShoppingCart.isPresent()) {
            shoppingCart = optionalShoppingCart.get();
        } else {
            shoppingCart = new ShoppingCart();
            shoppingCartRepository.save(shoppingCart);
        }

        // Carregar o produto
        ProductModel product = productRepository.findById(productId).orElse(null);
        if (product != null) {
            boolean itemFound = false;

            // Verificar se o item já existe no carrinho
            for (CartItem item : shoppingCart.getItems()) {
                if (item.getProduct().getProductId() == (productId)) {
                    item.setQuantity(item.getQuantity() + quantity);
                    cartItemRepository.save(item);
                    itemFound = true;
                    break;
                }
            }

            // Se o item não foi encontrado, adicionar um novo item ao carrinho
            if (!itemFound) {
                CartItem newCartItem = new CartItem();
                newCartItem.setProduct(product);
                newCartItem.setQuantity(quantity);
                newCartItem.setShoppingCart(shoppingCart);
                cartItemRepository.save(newCartItem);
                shoppingCart.getItems().add(newCartItem);
            }

            shoppingCartRepository.save(shoppingCart);
        } else {
            throw new RuntimeException("Product with id: " + productId + " not found");
        }
    }

    public void increaseItemQuantity(Long shoppingCartId, Long productId) {
        Optional<ShoppingCart> cartOptional = shoppingCartRepository.findById(shoppingCartId);
        if (cartOptional.isPresent()) {
            ShoppingCart shoppingCart = cartOptional.get();
            CartItem itemToIncrease = shoppingCart.getItems().stream()
                    .filter(item -> item.getProduct().getProductId() == (productId))
                    .findFirst().orElse(null);

            if (itemToIncrease != null) {
                itemToIncrease.setQuantity(itemToIncrease.getQuantity() + 1);
                cartItemRepository.save(itemToIncrease);
                LOGGER.info("Quantity of product with id: {} increased by 1", productId);
                shoppingCartRepository.save(shoppingCart);
            } else {
                LOGGER.warn("Product with id: {} not found in cart", productId);
            }
        } else {
            LOGGER.error("Shopping cart with id: {} not found", shoppingCartId);
        }
    }

    public void decreaseItemQuantity(Long shoppingCartId, Long productId) {
        Optional<ShoppingCart> cartOptional = shoppingCartRepository.findById(shoppingCartId);
        if (cartOptional.isPresent()) {
            ShoppingCart shoppingCart = cartOptional.get();
            CartItem itemToDecrease = shoppingCart.getItems().stream()
                    .filter(item -> item.getProduct().getProductId() == (productId))
                    .findFirst().orElse(null);

            if (itemToDecrease != null) {
                if (itemToDecrease.getQuantity() > 1) {
                    itemToDecrease.setQuantity(itemToDecrease.getQuantity() - 1);
                    cartItemRepository.save(itemToDecrease);
                    LOGGER.info("Quantity of product with id: {} decreased by 1", productId);
                } else {
                    shoppingCart.getItems().remove(itemToDecrease);
                    cartItemRepository.delete(itemToDecrease);
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

    @Transactional
    public void clearShoppingCart(Long shoppingCartId) {
        Optional<ShoppingCart> shoppingCartOptional = shoppingCartRepository.findById(shoppingCartId);
        if (shoppingCartOptional.isPresent()) {
            ShoppingCart shoppingCart = shoppingCartOptional.get();
            for (CartItem item : shoppingCart.getItems()) {
                cartItemRepository.delete(item);
            }
            shoppingCart.getItems().clear();
            shoppingCartRepository.save(shoppingCart);
        } else {
            throw new RuntimeException("Shopping Cart not found with id: " + shoppingCartId);
        }
    }
}
