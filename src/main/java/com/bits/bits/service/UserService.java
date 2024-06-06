package com.bits.bits.service;

import com.bits.bits.dto.AddressDTO;
import com.bits.bits.dto.AdminLoginDTO;
import com.bits.bits.dto.ClientDTO;
import com.bits.bits.dto.UserLoginDTO;
import com.bits.bits.exceptions.CannotAccessException;
import com.bits.bits.model.BillingAddressModel;
import com.bits.bits.model.UserAddressModel;
import com.bits.bits.model.UserModel;
import com.bits.bits.repository.UserRepository;
import com.bits.bits.util.UserRoles;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.h2.engine.User;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserService {

    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ViaCEPService viaCEPService;

    @Autowired
    private AuthenticationManager authenticationManager;

    public void addDeliveryAddress(Long userId, AddressDTO address) {
        UserModel user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User Not Found"));

        //até aqui dados são passados normalmente
        AddressDTO addressDTO = viaCEPService.findAddress(address.getCep());

        UserAddressModel deliveryAddress = getUserAddressModel(addressDTO, user);
        deliveryAddress.setComplemento(address.getComplemento());
        deliveryAddress.setNumero(address.getNumero());

        if (user.getUserAddress() == null) {
            user.setUserAddress(new ArrayList<>());
        }

        user.getUserAddress().add(deliveryAddress);

        userRepository.save(user);

    }

    public ResponseEntity<UserModel> updateUser(ClientDTO user, Long userId){

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Optional<UserModel> findUser = userRepository.findById(userId);

        if(findUser.isPresent()) {
            UserModel existingUser = findUser.get();

            if (user.getEmail() != null) {
                existingUser.setEmail(user.getEmail());
            }
            if (user.getName() != null) {
                existingUser.setName(user.getName());
            }
            if (user.getCpf() != null) {
                existingUser.setCpf(user.getCpf());
            }
            if (user.getGroup() != null) {
                existingUser.setGroup(user.getGroup());
            }
            if (user.getPassword() != null) {
                existingUser.setPassword(encoder.encode(user.getPassword()));
            }

            return ResponseEntity.ok(userRepository.save(existingUser));
        }
        return ResponseEntity.badRequest().build();
    }

    //metodo que faz o tratamento dos dados pegos na via cep (passa ao endereço)
    private static UserAddressModel getUserAddressModel(AddressDTO addressDTO, UserModel user) {
        UserAddressModel deliveryAddress = new UserAddressModel();
        deliveryAddress.setCep(addressDTO.getCep());
        deliveryAddress.setLogradouro(addressDTO.getLogradouro());
        deliveryAddress.setBairro(addressDTO.getBairro());
        deliveryAddress.setLocalidade(addressDTO.getLocalidade());
        deliveryAddress.setUf(addressDTO.getUf());
        //campos faltantes
        deliveryAddress.setNumero(addressDTO.getNumero());
        deliveryAddress.setComplemento(addressDTO.getComplemento());

        //aqui passa o modelo de usuario para o delivery address
        deliveryAddress.setUserModel(user);
        return deliveryAddress;
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

        user.setGroup(UserRoles.USER.getRole());

        addBillingAddress(user);

        if (user.getUserAddress() != null && !user.getUserAddress().isEmpty()) {
            user.getUserAddress().forEach(address -> address.setUserModel(user));
        }

        LOGGER.info("User successfully registered");
        return Optional.of(userRepository.save(user));
    }

    public void authenticateUser(UserLoginDTO userLoginDTO, HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userLoginDTO.getEmail(), userLoginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("user", userLoginDTO.getEmail());
        response.setHeader("X-Auth-Token", httpSession.getId());

        LOGGER.info("Session created successfully, token: {}", httpSession.getId());
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
