package com.cinemaster.backend.data.service;

import com.cinemaster.backend.data.dto.AccountDto;
import com.cinemaster.backend.data.dto.AccountPasswordLessDto;

import java.util.Optional;

public interface AccountService {

    Optional<AccountPasswordLessDto> checkCredentials(String username, String hashedPassword);

    void save(AccountDto accountDto);

    void update(AccountPasswordLessDto accountDto);

    AccountPasswordLessDto changePassword(Long accountId, String hashedPassword);
}
