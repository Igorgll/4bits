package com.bits.bits.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
    private long userId;

    @Email(message = "Email must be in the right format")
    @NotBlank(message = "Email cannot be null")
    @Size(max = 255, message = "Attribute email can have a maximum of 255 characters")
    private String email;

    @NotBlank(message = "Attribute name cannot be null")
    @Size(max = 100, message = "Attribute can have a maximum of 100 characters")
    private String name;

    @NotBlank(message = "Attribute CPF cannot be null")
    @Size(min = 14, message = "Attribute CPF must have a minimum of 14 characters")
    private String cpf;

    @NotBlank(message = "Password cannot be null")
    @Size(min = 8, message = "Attribute password must have a minimum of 8 characters")
    private String password;

    @NotNull
    private boolean admin;

    @NotNull
    private boolean isActive;
}
