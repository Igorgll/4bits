package com.bits.bits.repository;

import com.bits.bits.model.EstoquistaModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EstoquistaRepository extends JpaRepository<EstoquistaModel, Long> {
    List<EstoquistaModel> findEstoquistaByNameContainingIgnoreCase(String name);
    Optional<EstoquistaModel> findEstoquistaByEmail(String email);

}
