package com.mbas.E_commerce.repository;

import com.mbas.E_commerce.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
