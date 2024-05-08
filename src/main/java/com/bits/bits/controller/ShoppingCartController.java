package com.bits.bits.controller;

import com.bits.bits.model.ProductModel;
import com.bits.bits.model.ShoppingCart;
import com.bits.bits.repository.ShoppingCartRepository;
import com.bits.bits.service.ShoppingCartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/cart")
@CrossOrigin(origins = "*", allowedHeaders = "*")

public class ShoppingCartController {

    @Autowired
    private ShoppingCartService shoppingCartService;

    @Autowired
    private ShoppingCartRepository shoppingCartRepository;

    @GetMapping("viewCart")
    public List<ShoppingCart> viewCart(){
        return shoppingCartRepository.findAll();
    }

    @PostMapping("addItem")
    public void addItemToShoppingCart(@RequestBody ProductModel product, @RequestParam int quantity) {
        shoppingCartService.addItemToShoppingCart(product, quantity);
    }

}
