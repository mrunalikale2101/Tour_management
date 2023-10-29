package com.tourmanagement.Controllers;

import com.tourmanagement.DTOs.Request.LoginDTO;
import com.tourmanagement.DTOs.Request.RefreshTokenDTO;
import com.tourmanagement.DTOs.Request.RegisterDTO;
import com.tourmanagement.DTOs.Response.AccountRespDTO;
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
    public AccountRespDTO handleLogin(@Valid @RequestBody LoginDTO loginDTO) {
        return authService.login(loginDTO);
    }

    @PostMapping("/register")
    public String handleRegister(@Valid @RequestBody RegisterDTO registerDTO) {
        authService.register(registerDTO);

        return "Register new account successfully!";
    }

    @PostMapping("/logout")
    public String handleLogout() {
        authService.logout();

        return "Logout successfully!";
    }

    @PostMapping("/refresh-token")
    public AccountRespDTO handleRefreshToken(@RequestBody @Valid RefreshTokenDTO refreshTokenDTO) {
        return authService.refreshToken(refreshTokenDTO);
    }
}
