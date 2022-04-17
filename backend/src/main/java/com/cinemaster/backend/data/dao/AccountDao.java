package com.cinemaster.backend.data.dao;

import com.cinemaster.backend.data.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface AccountDao extends JpaRepository<Account, Long>, JpaSpecificationExecutor<Account> {

    Optional<Account> findByUsernameAndHashedPassword(String username, String hashedPassword);

    Optional<Account> findByUsername(String username);

    Optional<Account> findByUsernameAndIdNot(String username, Long id);
}
