package com.bits.bits.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientDTO {
    private Long userId;
    private String nome;
    private String email;
    private String password;
    private String cpf;

}
