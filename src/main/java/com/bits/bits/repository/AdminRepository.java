package com.bits.bits.repository;

import com.bits.bits.model.AdminModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<AdminModel, Long> {
    Optional<AdminModel> findUserByEmail(String email);

    List<AdminModel> findByNameContainingIgnoreCase(String name);


}
