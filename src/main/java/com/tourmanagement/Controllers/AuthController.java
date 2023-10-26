package com.tourmanagement.Controllers;

import com.tourmanagement.DTOs.LoginDTO;
import com.tourmanagement.DTOs.RegisterDTO;
import com.tourmanagement.Models.Account;
import com.tourmanagement.Services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@ResponseStatus(HttpStatus.OK)
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public Account handleLogin(@Valid @RequestBody LoginDTO loginDTO) {
        return authService.login(loginDTO);
    }

    @PostMapping("/register")
    public String handleRegister(@Valid @RequestBody RegisterDTO registerDTO) {
        authService.register(registerDTO);

        return "Register new account successfully!";
    }

    @PutMapping("/logout")
    public String handleLogout() {

        return "Logout successfully!";
    }
}
