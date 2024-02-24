package com.bits.bits.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int userId;

    @Email(message = "Email must be in the right format")
    @NotBlank(message = "Email cannot be null")
    private String email;

    @NotBlank(message = "Attribute name cannot be null")
    private String name;

    @NotBlank(message = "Password cannot be null")
    private String password;
}
