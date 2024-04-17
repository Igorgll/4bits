package com.bits.bits.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tb_billing_address")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BillingAddressModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long billingAdressId;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserModel userModel;

    @NotBlank(message = "CEP cannot be null")
    @Size(max = 8, message = "CEP has a 8 character limit")
    private String cep;

    @NotBlank(message = "Logradouro cannot be null")
    @Size(max = 255, message = "Logradouro has a 255 limit character")
    private String logradouro;

    @NotBlank(message = "Numero cannot be null")
    @Size(max = 8, message = "Numero has a 8 character limit")
    private String numero;

    @Size(max = 50, message = "Complemento has a 50 character limit")
    private String complemento;

    @NotBlank(message = "Bairro cannot be null")
    @Size(max = 255, message = "Bairro has a 255 character limit")
    private String bairro;

    @NotBlank(message = "Cidade cannot be null")
    @Size(max = 255, message = "Cidade has a 255 character limit")
    private String cidade;

    @NotBlank(message = "UF cannot be null")
    @Size(max = 2, message = "UF has a 2 character limit")
    private String uf;

}
