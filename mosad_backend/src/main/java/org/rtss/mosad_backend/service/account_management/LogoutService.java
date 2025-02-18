package org.rtss.mosad_backend.service.account_management;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.rtss.mosad_backend.entity.user_management.BlackListTokens;
import org.rtss.mosad_backend.entity.user_management.TokenType;
import org.rtss.mosad_backend.repository.user_management.BlackListTokensRepo;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@Service
public class LogoutService implements LogoutHandler {

    private final BlackListTokensRepo blackListTokensRepo;

    public LogoutService(BlackListTokensRepo blackListTokensRepo) {
        this.blackListTokensRepo = blackListTokensRepo;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Cookie[] cookies = request.getCookies();
        String refreshToken ="";
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("refreshToken".equals(cookie.getName())) {
                    refreshToken = cookie.getValue();
                    break;
                }
            }
        }else{
            throw new HttpServerErrorException(HttpStatus.UNAUTHORIZED,"No valid refresh token found.");
        }

        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        String jwtToken = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwtToken = authHeader.substring(7);
            blackListTokensRepo.saveAndFlush(new BlackListTokens(
                    jwtToken.trim(),
                    TokenType.ACCESS_TOKEN
            ));
        }

        // Blacklist the refresh token
        if (!refreshToken.isEmpty()) {
            blackListTokensRepo.saveAndFlush(new BlackListTokens(
                    refreshToken,
                    TokenType.REFRESH_TOKEN
            ));
        }

        Cookie cookie = new Cookie("refreshToken", null);
        cookie.setPath("/");
        cookie.setMaxAge(0); // Set expiration date to immediately expire it
        response.addCookie(cookie);
        try {
            new ObjectMapper().writeValue(response.getOutputStream(),"Successfully logged out");
        } catch (IOException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"ObjectMapper writing value error", e);
        }
    }

}
