package com.tourmanagement.Services;

import com.tourmanagement.DTOs.LoginDTO;
import com.tourmanagement.DTOs.RegisterDTO;
import com.tourmanagement.Models.Account;
import com.tourmanagement.Shared.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.logging.Logger;

@Service
public class AuthService {
    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    Logger logger = Logger.getLogger(AuthService.class.getName());

    @Autowired
    public AuthService(AccountService accountService, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.accountService  =  accountService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public Account login(LoginDTO loginDTO) {
        Optional<Account> matchedAccount = accountService.findAccoutByUsername(loginDTO.getUsername());

        if(matchedAccount.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username is wrong!");
        }

        boolean isMatchedPassword = passwordEncoder.matches(loginDTO.getPassword(), matchedAccount.get().getPassword());

        if(!isMatchedPassword) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password or username is wrong!");
        }

        String accessToken = jwtService.generateToken(accountService.userDetailsService().loadUserByUsername(loginDTO.getUsername()), Constant.ACCESS_TYPE);
        String refreshToken = jwtService.generateToken(accountService.userDetailsService().loadUserByUsername(loginDTO.getUsername()), Constant.REFRESH_TYPE);

        Account updatedAccount = matchedAccount.get();
        updatedAccount.setAccessToken(accessToken);
        updatedAccount.setRefreshToken(refreshToken);

        return accountService.updateAccount(updatedAccount);
    }

    public void register(RegisterDTO registerDTO) {
        Optional<Account> existedUsername = accountService.findAccoutByUsername(registerDTO.getUsername());

        if(existedUsername.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username existed!");
        }

        String hashedPassword = passwordEncoder.encode(registerDTO.getPassword());
        Account newAccount = new Account(registerDTO.getUsername(), hashedPassword);

        accountService.createNewAccount(newAccount);
    }

    public void logout() {

    }
}
