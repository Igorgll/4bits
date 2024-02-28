package com.bits.bits.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_cart")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class CartModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int CartID;

    @OneToOne
    @NotBlank(message = "Attribute userID cannot be null")
    private UserModel userModel;

    @ManyToOne
    @NotBlank(message = "Attribute productID cannot be null")
    private ProducModel producModel; 

    @NotBlank(message = "Attribute quantity cannot be null")
    private int quantity;
}
