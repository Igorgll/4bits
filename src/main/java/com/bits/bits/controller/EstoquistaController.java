package com.bits.bits.controller;

import com.bits.bits.dto.EstoquistaLoginDTO;
import com.bits.bits.exceptions.UserNotFoundException;
import com.bits.bits.model.EstoquistaModel;
import com.bits.bits.repository.EstoquistaRepository;
import com.bits.bits.service.EstoquistaService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/estoquistas")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class EstoquistaController {

    @Autowired
    private EstoquistaRepository estoquistaRepository;

    @Autowired
    private EstoquistaService estoquistaService;

    @GetMapping("/all")
    public List<EstoquistaModel> getAllEstoquistas(){ return estoquistaRepository.findAll(); }

    @GetMapping("/name/{name}")
    public List<EstoquistaModel> getAllEstoquistasByName(@PathVariable String name){
        List<EstoquistaModel> estoquistaList = estoquistaRepository.findEstoquistaByNameContainingIgnoreCase(name);
        if (estoquistaList.isEmpty()) {
            throw new UserNotFoundException();
        }
        return estoquistaList;
    }

    @GetMapping("/email/{email}")
    public Optional<EstoquistaModel> getEstoquistaByEmail(@PathVariable String email){
        Optional<EstoquistaModel> estoquista = estoquistaRepository.findEstoquistaByEmail(email);
        if (estoquista.isEmpty()) {
            throw new UserNotFoundException();
        }
        return estoquista;
    }

    @GetMapping("/{estoquistaId}")
    public ResponseEntity<EstoquistaModel> getEstoquistaById(@PathVariable Long estoquistaId){
        return estoquistaRepository.findById(estoquistaId)
                .map(resp -> ResponseEntity.status(HttpStatus.OK)
                        .body(resp)).orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @PostMapping("/signup")
    public ResponseEntity<EstoquistaModel> createEstoquista(@Valid @RequestBody EstoquistaModel estoquista) {
        return estoquistaService.estoquistaSignUp(estoquista)
                .map(resp -> ResponseEntity.status(HttpStatus.OK)
                        .body(resp)).orElse(ResponseEntity.status(HttpStatus.BAD_REQUEST).build());
    }

    @PutMapping("/updateEstoquista/{estoquistaId}")
    public ResponseEntity<EstoquistaModel> updateAdmin(@PathVariable Long estoquistaId, @Valid @RequestBody EstoquistaModel estoquista) {
        EstoquistaModel updateEstoquista = estoquistaService.updateEstoquista(estoquistaId, estoquista).getBody();
        return ResponseEntity.ok(updateEstoquista);
    }

    @PatchMapping("/isEstoquistaActive/{estoquistaId}/{isActive}")
    public ResponseEntity<EstoquistaModel> isEstoquistaActive(@PathVariable Long estoquistaId, @RequestParam boolean isActive) {
        EstoquistaModel updateEstoquistaStatus = estoquistaService.changeIsEstoquistaActive(estoquistaId, isActive).getBody();
        return ResponseEntity.ok(updateEstoquistaStatus);
    }

    @PostMapping("/login")
    public ResponseEntity<String> authenticateEstoquista(@RequestBody EstoquistaLoginDTO estoquistaLoginDTO, HttpServletRequest request, HttpServletResponse response) {
        estoquistaService.authenticateEstoquista(estoquistaLoginDTO, request, response);
        return new ResponseEntity<>("Estoquista login successfully", HttpStatus.OK);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpServletRequest request) {
        HttpSession httpSession = request.getSession(false);
        if (httpSession != null) {
            httpSession.invalidate();
        }
        return new ResponseEntity<>("Estoquista logged out successfully!", HttpStatus.OK);
    }

}
