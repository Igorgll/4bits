package com.bits.bits.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "tb_user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Email(message = "Email must be in the right format")
    @Size(max = 255, message = "Email can have a maximum of 255 characters")
    @NotBlank(message = "Email field cannot be null")
    private String email;

    @NotBlank(message = "CPF field cannot be null")
    @Size(min = 14, message = "CPF must have a minimum of 14 characters")
    private String cpf;

    @OneToMany(mappedBy = "user", cascade = CascadeType.MERGE)
    @JsonIgnoreProperties("user")
    @NotBlank(message = "User address must have all fields")
    private List<UserAddressModel> userAddress;

    @OneToOne(mappedBy = "user", cascade = CascadeType.MERGE)
    @JsonIgnoreProperties("user")
    @NotBlank(message = "Billing address must have all fields")
    private BillingAddressModel billingAddress;

    @NotBlank(message = "Client name cannot be null")
    @Size(min = 3, message = "Client name must have at least 3 characters on each word;")
    private String nome;

    @NotBlank(message = "User password cannot be null")
    @Size(min = 8, message = "User password must have a minimum of 8 characters")
    private String password;





}
