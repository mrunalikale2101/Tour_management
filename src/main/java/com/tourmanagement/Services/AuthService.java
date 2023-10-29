package com.tourmanagement.Services;

import com.tourmanagement.DTOs.Request.LoginDTO;
import com.tourmanagement.DTOs.Request.RefreshTokenDTO;
import com.tourmanagement.DTOs.Request.RegisterDTO;
import com.tourmanagement.DTOs.Response.AccountRespDTO;
import com.tourmanagement.Models.Account;
import com.tourmanagement.Shared.Constant;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
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
    private final ModelMapper modelMapper;
    Logger logger = Logger.getLogger(AuthService.class.getName());

    @Autowired
    public AuthService(AccountService accountService, PasswordEncoder passwordEncoder, JwtService jwtService, ModelMapper modelMapper) {
        this.accountService  =  accountService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.modelMapper = modelMapper;
    }

    public AccountRespDTO login(LoginDTO loginDTO) {
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

        updatedAccount = accountService.updateAccount(updatedAccount);

        return modelMapper.map(updatedAccount, AccountRespDTO.class);
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
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Optional<Account> matchedAccount = accountService.findAccoutByUsername(username);

        if(matchedAccount.isEmpty()) {
            return;
        }

        matchedAccount.get().setRefreshToken(null);
        matchedAccount.get().setAccessToken(null);

        accountService.updateAccount(matchedAccount.get());
    }

    public AccountRespDTO refreshToken( RefreshTokenDTO refreshTokenDTO) {
        try {
            String username = jwtService.extractUserName(refreshTokenDTO.getRefreshToken(), Constant.REFRESH_TYPE);

            if(username.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid token!");
            }

            Optional<Account> matchedAccount = accountService.findAccoutByUsername(username);

            if(matchedAccount.isEmpty() || !matchedAccount.get().getRefreshToken().equals(refreshTokenDTO.getRefreshToken())) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Invalid token!");
            }

            String accessToken = jwtService.generateToken(accountService.userDetailsService().loadUserByUsername(username), Constant.ACCESS_TYPE);
            String refreshToken = jwtService.generateToken(accountService.userDetailsService().loadUserByUsername(username), Constant.REFRESH_TYPE);

            Account updatedAccount = matchedAccount.get();
            updatedAccount.setAccessToken(accessToken);
            updatedAccount.setRefreshToken(refreshToken);

            updatedAccount = accountService.updateAccount(updatedAccount);

            return modelMapper.map(updatedAccount, AccountRespDTO.class);
        } catch (SignatureException e) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, e.getMessage());
        } catch (ExpiredJwtException e) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }
}
