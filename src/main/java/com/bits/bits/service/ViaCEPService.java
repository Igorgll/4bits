package com.bits.bits.service;

import com.bits.bits.dto.AddressDTO;
import com.bits.bits.model.BillingAddressModel;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ViaCEPService {

    private static final String VIA_CEP_URL = "https://viacep.com.br/ws/{cep}/json/";

    public AddressDTO findAddress(String cep) {
        String url = VIA_CEP_URL.replace("{cep}", cep);
        RestTemplate restTemplate = new RestTemplate();
        AddressDTO address = restTemplate.getForObject(url, AddressDTO.class);
        return address;
    }

}
