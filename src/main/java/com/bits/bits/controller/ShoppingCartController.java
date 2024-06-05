package com.bits.bits.controller;

import com.bits.bits.model.ShoppingCart;
import com.bits.bits.repository.ShoppingCartRepository;
import com.bits.bits.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/cart")
@CrossOrigin(origins = "*", allowedHeaders = "*")

public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @GetMapping("/viewCart")
    public List<ShoppingCart> viewCart(){
        return shoppingCartRepository.findAll();
    }

    @GetMapping("/viewCart/{shoppingCartId}")
    public Optional<ShoppingCart> viewCartByShoppingCartById(@PathVariable Long shoppingCartId) {
        return shoppingCartRepository.findById(shoppingCartId);
    };

    @PostMapping("/addItem/{productId}")
    public void addItemToShoppingCart(@PathVariable Long productId, @RequestParam int quantity) {
        shoppingCartService.addProductToShoppingCart(productId, quantity);
    }

    @PatchMapping("/increaseItem/{shoppingCartId}/{productId}")
    public void increaseItemQuantity(@PathVariable Long shoppingCartId, @PathVariable Long productId) {
        shoppingCartService.increaseItemQuantity(shoppingCartId, productId);
    }

    @PatchMapping("/decreaseItem/{shoppingCartId}/{productId}")
    public void decreaseItemQuantity(@PathVariable Long shoppingCartId, @PathVariable Long productId) {
        shoppingCartService.decreaseItemQuantity(shoppingCartId, productId);
    }

    @DeleteMapping("/removeItem/{shoppingCartId}/{productId}")
    public void removeItemFromShoppingCart(@PathVariable Long shoppingCartId, @PathVariable Long productId, @RequestParam int quantity){
        shoppingCartService.removeProductFromShoppingCart(shoppingCartId, productId, quantity);
    }

}
