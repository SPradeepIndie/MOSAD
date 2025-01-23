package org.rtss.mosad_backend.service.login_user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rtss.mosad_backend.dto.user_dtos.AuthDTO;
import org.rtss.mosad_backend.dto.user_dtos.UserLoginDTO;
import org.rtss.mosad_backend.entity.user_management.Users;
import org.rtss.mosad_backend.entity.user_management.UserRoles;
import org.rtss.mosad_backend.repository.user_management.UsersRepo;
import org.rtss.mosad_backend.validator.DtoValidator;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.client.HttpServerErrorException;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoginServiceTest {

    //Service to be tested
    private LoginService loginService;

    private AuthenticationManager authenticationManager;
    private JwtService jwtService;
    private DtoValidator dtoValidator;
    private UserLoginDTO userLoginDto;
    private Authentication authentication;

    private UsersRepo usersRepo;
    private AuthDTO authDTO;

    @BeforeEach
    void setUp() {
        authenticationManager=mock(AuthenticationManager.class);
        jwtService=mock(JwtService.class);
        dtoValidator=mock(DtoValidator.class);
        usersRepo=mock(UsersRepo.class);
        authentication=mock(Authentication.class);

        authDTO=new AuthDTO();
        userLoginDto = new UserLoginDTO("testUser", "testPassword");

        loginService=new LoginService(authenticationManager,jwtService,dtoValidator,usersRepo);
    }

    //**********************
    // Normal login request
    //*********************
    @Test
    void shouldVerifyUserAndGenerateTokensWhenAuthenticated() {
        //Given
        UserRoles userRoles=new UserRoles();
        userRoles.setRoleName("Admin");
        Users user =new Users();
        user.setUsername("testUser");
        user.setPassword("testPassword");
        user.setFirstName("testFirstName");
        user.setLastName("testLastName");
        user.setEmail("testEmail@gmail.com");
        user.setUserRoles(userRoles);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(Boolean.TRUE);

        when(authentication.getPrincipal()).thenReturn(user);

        when(jwtService.generateToken(userLoginDto.getUsername(),"Admin")).thenReturn("accessToken");
        when(jwtService.generateRefreshToken(userLoginDto.getUsername())).thenReturn("refreshToken");

        //When
        AuthDTO result = loginService.verifyUser(userLoginDto);

        //Then
        assertTrue(result.isAuthenticated());
        assertEquals("accessToken",result.getAccessToken());
        assertEquals("refreshToken",result.getRefreshToken());

        verify(dtoValidator).validate(userLoginDto);
        verify(authenticationManager).authenticate(any());
        verify(jwtService).generateToken(userLoginDto.getUsername(),"Admin");
        verify(jwtService).generateRefreshToken(userLoginDto.getUsername());
    }

    @Test
    void shouldNotVerifyUserAndShouldNotGenerateTokensWhenAuthenticationFailed() {
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(false);

        //When
        AuthDTO result = loginService.verifyUser(userLoginDto);

        //Then
        assertFalse(result.isAuthenticated());
        assertNull(result.getAccessToken());
        assertNull(result.getRefreshToken());

        verify(dtoValidator).validate(userLoginDto);
        verify(authenticationManager).authenticate(any());
        verify(jwtService, never()).generateToken(any(),any());
        verify(jwtService, never()).generateRefreshToken(any());
    }

    //**********************
    // access token refresh
    //*********************
    @Test
    void shouldGenerateAccessTokenWhenHeaderIsValidAndRefreshTokenIsValid(){
        //Given
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        String refreshToken = "testRefreshToken";
        String username = "testUser";
        request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + refreshToken);

        UserRoles userRoles=new UserRoles();
        userRoles.setRoleName("Admin");
        Users user =new Users();
        user.setUsername("testUser");
        user.setPassword("testPassword");
        user.setFirstName("testFirstName");
        user.setLastName("testLastName");
        user.setEmail("testEmail@gmail.com");
        user.setUserRoles(userRoles);

        when(jwtService.extractUsernameFromToken(refreshToken)).thenReturn(username);
        when(usersRepo.findByUsername(username)).thenReturn(Optional.of(user));
        when(jwtService.validateToken(refreshToken, user)).thenReturn(true);
        when(jwtService.generateToken(username,"Admin")).thenReturn("newAccessToken");

        authDTO.setAccessToken("newAccessToken");
        authDTO.setRefreshToken(refreshToken);
        authDTO.setAuthenticated(true);

        //test that output stream has written

        //When
        try {
            loginService.refreshToken(request, response);
        } catch (IOException e) {
            fail("IOException should not have been thrown: " + e.getMessage());
        }

        //Then
        verify(jwtService).extractUsernameFromToken(refreshToken);
        verify(usersRepo).findByUsername(username);
        verify(jwtService).validateToken(refreshToken,user);
        verify(jwtService).generateToken(username,"Admin");

        assertTrue(authDTO.isAuthenticated());
        assertEquals("newAccessToken",authDTO.getAccessToken());
        assertEquals(refreshToken,authDTO.getRefreshToken());

    }

    @Test
    void shouldFailToRefreshTokenWithNullAuthHeader() {
        //Given
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();

        //when login service is called and then throw exception
        HttpServerErrorException exception = assertThrows(HttpServerErrorException.class,
                ()-> loginService.refreshToken(request,response));

        //then
        assertEquals(HttpStatus.BAD_REQUEST,exception.getStatusCode());
        assertEquals("Invalid request header",exception.getStatusText());
    }

    @Test
    void shouldFailToRefreshTokenWithEmptyAuthHeader() {
        //Given
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        request.addHeader(HttpHeaders.AUTHORIZATION, "");

        //when login service is called and then throw exception
        HttpServerErrorException exception = assertThrows(HttpServerErrorException.class,
                ()-> loginService.refreshToken(request,response));

        //Then
        assertEquals(HttpStatus.BAD_REQUEST,exception.getStatusCode());
        assertEquals("Invalid request header",exception.getStatusText());
    }

    @Test
    void shouldFailToRefreshTokenWhenTokenInvalid(){
        //Given
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        String refreshToken = "testRefreshToken";
        String username = "testUser";
        request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + refreshToken);

        UserRoles userRoles=new UserRoles();
        userRoles.setRoleName("Admin");
        Users user =new Users();
        user.setUsername("testUser");
        user.setPassword("testPassword");
        user.setFirstName("testFirstName");
        user.setLastName("testLastName");
        user.setEmail("testEmail@gmail.com");
        user.setUserRoles(userRoles);

        when(jwtService.extractUsernameFromToken(refreshToken)).thenReturn(username);
        when(usersRepo.findByUsername(username)).thenReturn(Optional.of(user));
        when(jwtService.validateToken(refreshToken, user)).thenReturn(false);

        //when login service is called and then throw exception
        HttpServerErrorException exception=assertThrows(HttpServerErrorException.class,
                ()->loginService.refreshToken(request, response));

        //Then
        assertEquals(HttpStatus.UNAUTHORIZED,exception.getStatusCode());
        assertEquals("Invalid refresh token",exception.getStatusText());

        verify(jwtService).extractUsernameFromToken(refreshToken);
        verify(usersRepo).findByUsername(username);
        verify(jwtService).validateToken(refreshToken,user);
    }

    @Test
    void shouldFailToRefreshTokenWhenUsernameIsNull(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        MockHttpServletResponse response = new MockHttpServletResponse();
        String refreshToken = "testRefreshToken";
        request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + refreshToken);

        when(jwtService.extractUsernameFromToken(refreshToken)).thenReturn(null);

        //when login service is called and then throw exception
        HttpServerErrorException exception = assertThrows(HttpServerErrorException.class,
                ()-> loginService.refreshToken(request,response));

        //Then
        assertEquals(HttpStatus.UNAUTHORIZED,exception.getStatusCode());
        assertEquals("Invalid refresh token",exception.getStatusText());

        verify(jwtService).extractUsernameFromToken(refreshToken);
    }


}