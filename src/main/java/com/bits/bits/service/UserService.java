package com.bits.bits.service;

import com.bits.bits.dto.AddressDTO;
import com.bits.bits.exceptions.CannotAccessException;
import com.bits.bits.model.BillingAddressModel;
import com.bits.bits.model.UserAddressModel;
import com.bits.bits.model.UserModel;
import com.bits.bits.repository.UserRepository;
import org.h2.engine.User;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ViaCEPService viaCEPService;

    public void addDeliveryAddress(Long userId, String cep) {
        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        AddressDTO addressDTO = viaCEPService.findAddress(cep);

        UserAddressModel deliveryAddress = new UserAddressModel();
        deliveryAddress.setCep(addressDTO.getCep());
        deliveryAddress.setLogradouro(addressDTO.getLogradouro());
        deliveryAddress.setBairro(addressDTO.getBairro());
        deliveryAddress.setLocalidade(addressDTO.getLocalidade());
        deliveryAddress.setUf(addressDTO.getUf());
        deliveryAddress.setNumero(addressDTO.getNumero());
        deliveryAddress.setComplemento(addressDTO.getComplemento());

        if (user.getUserAddress() == null) {
            user.setUserAddress(new ArrayList<>());
        }

        user.getUserAddress().add(deliveryAddress);

        userRepository.save(user);

    }

    @Transactional
    public Optional<UserModel> userSignUp(UserModel user) {
        Optional<UserModel> findUser = userRepository.findUserByEmail(user.getEmail());

        if(findUser.isPresent()) {
            LOGGER.info("User already exists");
            throw new CannotAccessException();
        }

        BCryptPasswordEncoder criptografar = new BCryptPasswordEncoder();
        String senhaCriptografada = criptografar.encode(user.getPassword());
        user.setPassword(senhaCriptografada);

        addBillingAddress(user);

        if (user.getUserAddress() != null && !user.getUserAddress().isEmpty()) {
            user.getUserAddress().forEach(address -> address.setUserModel(user));
        }

        LOGGER.info("User successfully registered");
        return Optional.of(userRepository.save(user));
    }

    private void addBillingAddress(UserModel user){
        AddressDTO addressDTO = viaCEPService.findAddress(user.getBillingAddress().getCep());
        BillingAddressModel billingAddressModel = user.getBillingAddress();
        billingAddressModel.setUserModel(user);
        billingAddressModel.setLogradouro(addressDTO.getLogradouro());
        billingAddressModel.setBairro(addressDTO.getBairro());
        billingAddressModel.setLocalidade(addressDTO.getLocalidade());
        billingAddressModel.setUf(addressDTO.getUf());
        user.setBillingAddress(billingAddressModel);
    }
}
