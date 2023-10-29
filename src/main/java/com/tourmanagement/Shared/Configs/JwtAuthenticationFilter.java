package com.tourmanagement.Shared.Configs;

import com.tourmanagement.Models.Account;
import com.tourmanagement.Services.AccountService;
import com.tourmanagement.Services.JwtService;
import com.tourmanagement.Shared.Constant;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final AccountService accountService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain)
            throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");
        final String jwt;
        String username = "";
        if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, "Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        jwt = authHeader.substring(7);
        try {
            username = jwtService.extractUserName(jwt, Constant.ACCESS_TYPE);
        }catch (SignatureException e) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType("application/json");
            response.getWriter().write("{ \"message\": \"Forbidden resource!\", \"statusCode\": 403, \"error\": \"" + e.getMessage() + "\" }");
            return;
        } catch (ExpiredJwtException e) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType("application/json");
            response.getWriter().write("{ \"message\": \"Unauthorized!\", \"statusCode\": 401, \"error\": \"" + e.getMessage() + "\" }");
        }

        if(StringUtils.isEmpty(username)) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType("application/json");
            response.getWriter().write("{ \"message\": \"Forbidden resource!\", \"statusCode\": 403, \"error\": \"" + "Invalid token!" + "\" }");
            return;
        }

        Optional<Account> matchedAccount = accountService.findAccoutByUsername(username);
        if(matchedAccount.isEmpty() || !matchedAccount.get().getAccessToken().equals(jwt)) {
            response.setStatus(HttpStatus.FORBIDDEN.value());
            response.setContentType("application/json");
            response.getWriter().write("{ \"message\": \"Forbidden resource!\", \"statusCode\": 403, \"error\": \"" + "Invalid token!" + "\" }");
            return;
        }

        if (StringUtils.isNotEmpty(username) && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = accountService.userDetailsService().loadUserByUsername(username);
            if (jwtService.isTokenValid(jwt, userDetails, Constant.ACCESS_TYPE)) {
                SecurityContext context = SecurityContextHolder.createEmptyContext();
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                context.setAuthentication(authToken);
                SecurityContextHolder.setContext(context);
            }
        }

        filterChain.doFilter(request, response);
    }
}