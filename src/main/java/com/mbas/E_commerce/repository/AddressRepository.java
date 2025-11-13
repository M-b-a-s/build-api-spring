package com.mbas.E_commerce.repository;

import com.mbas.E_commerce.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
