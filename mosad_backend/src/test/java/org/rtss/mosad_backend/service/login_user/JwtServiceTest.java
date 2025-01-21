package org.rtss.mosad_backend.service.login_user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.userdetails.UserDetails;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class JwtServiceTest {

    //service to be tested
    private JwtService jwtService;


    @BeforeEach
    void setUp() {
        jwtService = new JwtService();
    }

    @Test
    void generateToken_ShouldReturnValidToken() {
        // Given
        String username = "testUser";
        String role = "Admin";

        // When
        String token = jwtService.generateToken(username, role);

        // Then
        assertNotNull(token);
        assertTrue(token.startsWith("ey")); // JWT tokens start with 'ey'

        String extractedUsername = jwtService.extractUsernameFromToken(token);
        assertEquals(username, extractedUsername);
    }

    @Test
    void generateRefreshToken_ShouldGenerateValidRefreshToken() {
        //Given
        String username = "testUser";

        // When
        String refreshToken = jwtService.generateRefreshToken(username);

        // Then
        assertNotNull(refreshToken);
        assertTrue(refreshToken.startsWith("ey")); // JWT tokens start with 'ey'

        String extractedUsername = jwtService.extractUsernameFromToken(refreshToken);
        assertEquals(username, extractedUsername);
    }

    @Test
    void extractUsernameFromToken_ShouldReturnUsername() {
        //Given
        String username = "testUser";
        String role = "Admin";
        String token = jwtService.generateToken(username, role);

        //when
        String extractedUsername = jwtService.extractUsernameFromToken(token);

        //then
        assertEquals(username, extractedUsername);
    }

    @Test
    void validateToken_ShouldReturnTrue_WhenTokenIsValid() {
        //Given
        String username = "testUser";
        String role = "Admin";
        String token = jwtService.generateToken(username, role);
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn(username);

        //when
        boolean isValid = jwtService.validateToken(token, userDetails);

        //then
        assertTrue(isValid);
    }

    @Test
    void validateToken_ShouldReturnFalseForInvalidUsername() {
        // Given
        String token = jwtService.generateToken("testUser", "Admin");

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("wrongUser");

        // When
        boolean isValid = jwtService.validateToken(token, userDetails);

        // Then
        assertFalse(isValid);
    }



}

