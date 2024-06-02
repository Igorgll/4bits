package com.bits.bits.service;

import com.bits.bits.dto.AdminLoginDTO;
import com.bits.bits.dto.EstoquistaLoginDTO;
import com.bits.bits.exceptions.CannotAccessException;
import com.bits.bits.exceptions.UserNotFoundException;
import com.bits.bits.model.AdminModel;
import com.bits.bits.model.EstoquistaModel;
import com.bits.bits.repository.EstoquistaRepository;
import com.bits.bits.util.UserRoles;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EstoquistaService {

    private static final Logger LOGGER = LoggerFactory.getLogger(AdminService.class);

    @Autowired
    private EstoquistaRepository estoquistaRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    public Optional<EstoquistaModel> estoquistaSignUp(EstoquistaModel estoquista) {
        Optional<EstoquistaModel> findEstoquista = estoquistaRepository.findEstoquistaByEmail(estoquista.getEmail());

        if (findEstoquista.isPresent()) {
            LOGGER.info("Estoquista already exists");
            throw new CannotAccessException();
        }

        BCryptPasswordEncoder criptografar = new BCryptPasswordEncoder();
        String senhaCriptografada = criptografar.encode(estoquista.getPassword());
        estoquista.setPassword(senhaCriptografada);
        estoquista.setGroup(UserRoles.ESTOQUISTA.getRole());
        estoquista.setActive(true);

        LOGGER.info("Estoquista successfully registered");

        return Optional.of(estoquistaRepository.save(estoquista));
    }

    public ResponseEntity<EstoquistaModel> updateEstoquista(Long estoquistaId, EstoquistaModel estoquista) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Optional<EstoquistaModel> findEstoquista = estoquistaRepository.findById(estoquistaId);
        if (findEstoquista.isPresent()) {
            EstoquistaModel existingEstoquista = findEstoquista.get();

            existingEstoquista.setEmail(estoquista.getEmail());
            existingEstoquista.setName(estoquista.getName());
            existingEstoquista.setCpf(estoquista.getCpf());
            existingEstoquista.setPassword(encoder.encode(estoquista.getPassword()));
            existingEstoquista.setGroup(estoquista.getGroup());
            existingEstoquista.setActive(true);

            return ResponseEntity.ok(estoquistaRepository.save(existingEstoquista));
        }
        return ResponseEntity.badRequest().build();
    }

    public ResponseEntity<EstoquistaModel> changeIsEstoquistaActive(Long estoquistaId, boolean isActive) {
        Optional<EstoquistaModel> findEstoquista = estoquistaRepository.findById(estoquistaId);

        if (findEstoquista.isPresent()) {
            EstoquistaModel estoquista = findEstoquista.get();
            estoquista.setActive(isActive);
            LOGGER.info("Estoquista status successfully changed");
            return ResponseEntity.ok(estoquistaRepository.save(estoquista));
        } else {
            LOGGER.error("Estoquista not found with ID: " + estoquistaId);
            throw new UserNotFoundException();
        }
    }

    public void authenticateEstoquista(EstoquistaLoginDTO estoquistaLoginDTO, HttpServletRequest request, HttpServletResponse response) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(estoquistaLoginDTO.getEmail(), estoquistaLoginDTO.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        HttpSession httpSession = request.getSession();
        httpSession.setAttribute("estoquista", estoquistaLoginDTO.getEmail());
        response.setHeader("X-Auth-Token", httpSession.getId());

        LOGGER.info("Session created successfully, token: {}", httpSession.getId());
    }
}
