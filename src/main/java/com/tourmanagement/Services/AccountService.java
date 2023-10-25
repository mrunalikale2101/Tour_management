package com.tourmanagement.Services;

import com.tourmanagement.Models.Account;
import com.tourmanagement.Repositorys.AccountRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    public AccountService (AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public Optional<Account> findAccoutByUsername(String username) {
        Account account = accountRepository.findByUsername(username);

        return Optional.ofNullable(account);
    }

    public Account createNewAccount(Account newAccount) {
        return accountRepository.save(newAccount);
    }

    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                Account account = accountRepository.findByUsername(username);

                return new User(
                        account.getUsername(),
                        account.getPassword(),
                        true,
                        true,
                        true,
                        true,
                        new ArrayList<>()
                );
            }
        };
    }
}
