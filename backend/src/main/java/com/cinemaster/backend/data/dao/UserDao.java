package com.cinemaster.backend.data.dao;

import com.cinemaster.backend.data.entity.Account;
import com.cinemaster.backend.data.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserDao extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {

    Optional<Account> findByEmail(String email);

    Optional<Account> findByEmailAndIdNot(String email, Long id);
}
