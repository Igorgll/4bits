package com.bits.bits.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;

@Entity
@Table(name = "tb_cart_item")
@Getter
@Setter
@NoArgsConstructor
@ToString
public class CartItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long cartId;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "shopping_cart_id")
    private ShoppingCart shoppingCart;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private ProductModel product;

    @NotNull(message = "Quantity cannot be null.")
    private int quantity;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Orders order;

    public CartItem(ProductModel product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CartItem cartItem = (CartItem) o;
        return quantity == cartItem.quantity && Objects.equals(cartId, cartItem.cartId) && Objects.equals(shoppingCart, cartItem.shoppingCart) && Objects.equals(product, cartItem.product);
    }
}
