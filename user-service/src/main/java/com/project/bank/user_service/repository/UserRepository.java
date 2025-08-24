package com.project.bank.user_service.repository;

import com.project.bank.user_service.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository <User, UUID> {
//ay extra function b declare it hena

    boolean existsByUsernameIgnoreCase(String username); //before insert b check kan mwgoud wala no ashan duplicates
    boolean existsByEmailIgnoreCase(String email);
    Optional<User> findByUsernameIgnoreCase(String username);
    Optional<User> findByEmailIgnoreCase(String email); //optional ashan matrg3sh null

}
