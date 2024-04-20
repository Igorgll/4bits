package com.bits.bits.repository;

import com.bits.bits.model.UserAddressModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<UserAddressModel, Long> {

    Optional<UserAddressModel> findById(long addressId);

    @Query("SELECT u.cep FROM UserAddressModel u WHERE u.id = :addressId")
    String findCepById(@Param("addressId") Long addressId);

    @Query("SELECT u FROM UserAddressModel u WHERE u.cep = :cep")
    UserAddressModel findByCep(@Param("cep") String cep);

}
