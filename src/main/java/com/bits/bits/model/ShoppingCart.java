package com.bits.bits.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tb_shopping_cart")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShoppingCart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shoppingCartId;

    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartItem> items = new ArrayList<>();

    @Override
    public String toString() {
        return "ShoppingCart{" +
                "shoppingCartId=" + shoppingCartId +
                ", items=" + items +
                '}';
    }
}
