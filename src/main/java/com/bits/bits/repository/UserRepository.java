package com.bits.bits.repository;

import com.bits.bits.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Long> {

    Optional<UserModel> findUserByEmail(String email);

    UserModel findByEmail(String email);

    List<UserModel> findByNameContainingIgnoreCase(String name);

    Optional<UserModel> findById(long userId);

}
