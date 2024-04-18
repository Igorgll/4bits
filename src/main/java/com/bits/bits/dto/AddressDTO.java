package com.bits.bits.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO {

    private String cep;
    private String complemento;
    private String numero;
    private String logradouro;
    private String bairro;
    private String localidade;
    private String uf;

}
