package com.cinemaster.backend.data.service.impl;

import com.cinemaster.backend.core.exception.EmailAlreadyPresentException;
import com.cinemaster.backend.core.exception.InvalidDataException;
import com.cinemaster.backend.core.exception.UserNotFoundException;
import com.cinemaster.backend.core.exception.UsernameAlreadyPresentException;
import com.cinemaster.backend.data.dao.AccountDao;
import com.cinemaster.backend.data.dao.UserDao;
import com.cinemaster.backend.data.dto.*;
import com.cinemaster.backend.data.entity.Account;
import com.cinemaster.backend.data.entity.Admin;
import com.cinemaster.backend.data.entity.Cashier;
import com.cinemaster.backend.data.entity.User;
import com.cinemaster.backend.data.service.AccountService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Optional<AccountPasswordLessDto> checkCredentials(String username, String hashedPassword) {
        Optional<Account> optional = accountDao.findByUsernameAndHashedPassword(username, hashedPassword);
        if (optional.isPresent()) {
            return optional.map(account -> {
                if (account instanceof Admin) {
                    return modelMapper.map(account, AdminPasswordLessDto.class);
                } else if (account instanceof User) {
                    return modelMapper.map(account, UserPasswordLessDto.class);
                } else if (account instanceof Cashier) {
                    return modelMapper.map(account, CashierPasswordLessDto.class);
                } else {
                    return null;
                }
            });
        }
        return Optional.empty();
    }

    @Override
    @Transactional
    public void save(AccountDto accountDto) {
        Account account = null;
        if (accountDto instanceof AdminDto) {
            account = modelMapper.map(accountDto, Admin.class);
        } else if (accountDto instanceof UserDto) {
            account = modelMapper.map(accountDto, User.class);
            User user = (User) account;
            if (accountDao.findByUsername(user.getUsername()).isPresent()) {
                throw new UsernameAlreadyPresentException();
            }
            if (userDao.findByEmail(user.getEmail()).isPresent()) {
                throw new EmailAlreadyPresentException();
            }
        } else if (accountDto instanceof CashierDto) {
            account = modelMapper.map(accountDto, Cashier.class);
        }
        accountDao.save(account);
        accountDto.setId(account.getId());
    }

    @Override
    @Transactional
    public void update(AccountPasswordLessDto accountDto) {
        if (accountDto instanceof UserPasswordLessDto) {
            User user = modelMapper.map(accountDto, User.class);
            String originalPassword = accountDao.findById(accountDto.getId()).get().getHashedPassword();
            user.setHashedPassword(originalPassword);
            if (accountDao.findByUsernameAndIdNot(user.getUsername(), user.getId()).isPresent()) {
                throw new UsernameAlreadyPresentException();
            }
            if (userDao.findByEmailAndIdNot(user.getEmail(), user.getId()).isPresent()) {
                throw new EmailAlreadyPresentException();
            }
            accountDao.saveAndFlush(user);
        } else {
            throw new InvalidDataException();
        }
    }

    @Override
    @Transactional
    public AccountPasswordLessDto changePassword(Long accountId, String hashedPassword) {
        User user = userDao.findById(accountId).orElseThrow(() -> new UserNotFoundException());
        user.setHashedPassword(hashedPassword);
        userDao.saveAndFlush(user);
        return modelMapper.map(user, UserPasswordLessDto.class);
    }
}
