package org.rtss.mosad_backend.service.login_user;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.WriteListener;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.rtss.mosad_backend.dto.user_dtos.AuthDTO;
import org.rtss.mosad_backend.dto.user_dtos.UserLoginDTO;
import org.rtss.mosad_backend.entity.branch_management.Branch;
import org.rtss.mosad_backend.entity.user_management.Users;
import org.rtss.mosad_backend.entity.user_management.UserRoles;
import org.rtss.mosad_backend.repository.user_management.UsersRepo;
import org.rtss.mosad_backend.validator.DtoValidator;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.client.HttpServerErrorException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
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
    private HttpServletRequest request;
    private HttpServletResponse response;

    private UsersRepo usersRepo;
    private AuthDTO authDTO;

    @BeforeEach
    void setUp() {
        authenticationManager=mock(AuthenticationManager.class);
        jwtService=mock(JwtService.class);
        dtoValidator=mock(DtoValidator.class);
        usersRepo=mock(UsersRepo.class);
        authentication=mock(Authentication.class);
        request = mock(HttpServletRequest.class);
        response = mock(HttpServletResponse.class);

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
        String accessToken="accessToken";
        Users user =new Users();
        user.setUsername("testUser");
        user.setPassword("testPassword");
        user.setFirstName("testFirstName");
        user.setLastName("testLastName");
        user.setEmail("testEmail@gmail.com");

        UserRoles userRoles=new UserRoles();
        userRoles.setRoleName("Admin");
        user.setUserRoles(userRoles);

        Branch branch=new Branch();
        user.setBranch(branch);

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(Boolean.TRUE);

        when(authentication.getPrincipal()).thenReturn(user);

        when(jwtService.generateToken(userLoginDto.getUsername(),"Admin")).thenReturn(accessToken);
        when(jwtService.generateRefreshToken(userLoginDto.getUsername())).thenReturn("refreshToken");

        authDTO.setAuthenticated(true);
        authDTO.setAccessToken(accessToken);
        authDTO.setRole(userRoles.getRoleName());
        authDTO.setBranchId(user.getBranch().getBranchId());
        // Capture the output stream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        //When
        try {
            when(response.getOutputStream()).thenReturn(new TestServletOutputStream(outputStream));
            loginService.verifyUser(response,userLoginDto);
            // Assert that the correct object was written to the response.
            ObjectMapper objectMapper = new ObjectMapper();
            String expectedJson = objectMapper.writeValueAsString(authDTO);
            String actualJson = outputStream.toString();
            assertEquals(expectedJson, actualJson);
        } catch (IOException e) {
            fail("IOException should not have been thrown: " + e.getMessage());
        }

        //Then
        verify(dtoValidator).validate(userLoginDto);
        verify(authenticationManager).authenticate(any());
        verify(jwtService).generateToken(userLoginDto.getUsername(),"Admin");
        verify(jwtService).generateRefreshToken(userLoginDto.getUsername());
    }

    @Test
    void shouldNotVerifyUserAndShouldNotGenerateTokensWhenAuthenticationFailed() {
        //Given
        when(authenticationManager.authenticate(any())).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(false);

        authDTO.setAuthenticated(false);

        // Capture the output stream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            when(response.getOutputStream()).thenReturn(new TestServletOutputStream(outputStream));
            loginService.verifyUser(response,userLoginDto);
            // Assert that the correct object was written to the response.
            ObjectMapper objectMapper = new ObjectMapper();
            String expectedJson = objectMapper.writeValueAsString(authDTO);
            String actualJson = outputStream.toString();
            assertEquals(expectedJson, actualJson);
        } catch (IOException e) {
            fail("IOException should not have been thrown: " + e.getMessage());
        }

        //Then
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
        String refreshToken = "validRefreshToken";
        String username = "testUser";
        UserRoles userRoles=new UserRoles();
        userRoles.setRoleName("Admin");
        Users user =new Users();
        user.setUsername("testUser");
        user.setPassword("testPassword");
        user.setFirstName("testFirstName");
        user.setLastName("testLastName");
        user.setEmail("testEmail@gmail.com");
        user.setUserRoles(userRoles);

        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        Cookie[] cookies = {refreshTokenCookie};

        when(request.getCookies()).thenReturn(cookies);

        when(jwtService.extractUsernameFromToken(refreshToken)).thenReturn(username);
        when(usersRepo.findByUsername(username)).thenReturn(Optional.of(user));
        when(jwtService.validateToken(refreshToken, user)).thenReturn(true);
        when(jwtService.generateToken(username,"Admin")).thenReturn("newAccessToken");

        authDTO.setAccessToken("newAccessToken");
        authDTO.setAuthenticated(true);

        // Capture the output stream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            when(response.getOutputStream()).thenReturn(new TestServletOutputStream(outputStream));
            loginService.refreshToken(request,response);
            // Assert that the correct object was written to the response.
            ObjectMapper objectMapper = new ObjectMapper();
            String expectedJson = objectMapper.writeValueAsString(authDTO);
            String actualJson = outputStream.toString();
            assertEquals(expectedJson, actualJson);
        } catch (IOException e) {
            fail("IOException should not have been thrown: " + e.getMessage());
        }

        //Then
        verify(jwtService).extractUsernameFromToken(refreshToken);
        verify(usersRepo).findByUsername(username);
        verify(jwtService).validateToken(refreshToken,user);
        verify(jwtService).generateToken(username,"Admin");


    }

    @Test
    void refreshToken_noRefreshTokenCookie_throwsUnauthorizedException() {
        //Given
        when(request.getCookies()).thenReturn(null);

        //when login service is called and then throw exception
        HttpServerErrorException exception = assertThrows(HttpServerErrorException.class,
                ()-> loginService.refreshToken(request,response));

        //then
        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatusCode());
        assertEquals("No valid refresh token found.", exception.getStatusText());
    }

    @Test
    void refreshToken_emptyRefreshToken_throwsUnauthorizedException() {
        // Given
        Cookie refreshTokenCookie = new Cookie("refreshToken", "");
        Cookie[] cookies = {refreshTokenCookie};
        when(request.getCookies()).thenReturn(cookies);

        // When
        HttpServerErrorException exception = assertThrows(HttpServerErrorException.class,
                () -> loginService.refreshToken(request, response));

        //Then
        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatusCode());
        assertEquals("Refresh token is empty", exception.getStatusText());
    }

    @Test
    void refreshToken_nullUsername_throwsUnauthorizedException() {
        // Given
        Cookie refreshTokenCookie = new Cookie("refreshToken", "invalidRefreshToken");
        Cookie[] cookies = {refreshTokenCookie};

        when(request.getCookies()).thenReturn(cookies);
        when(jwtService.extractUsernameFromToken("invalidRefreshToken")).thenReturn(null);

        // When
        HttpServerErrorException exception = assertThrows(HttpServerErrorException.class,
                () -> loginService.refreshToken(request, response));

        //Then
        assertEquals(HttpStatus.UNAUTHORIZED, exception.getStatusCode());
        assertEquals("Invalid refresh token", exception.getStatusText());
    }

    @Test
    void shouldFailToRefreshTokenWhenTokenInvalid(){
        //Given
        String refreshToken = "testRefreshToken";
        String username = "testUser";

        UserRoles userRoles=new UserRoles();
        userRoles.setRoleName("Admin");
        Users user =new Users();
        user.setUsername("testUser");
        user.setPassword("testPassword");
        user.setFirstName("testFirstName");
        user.setLastName("testLastName");
        user.setEmail("testEmail@gmail.com");
        user.setUserRoles(userRoles);

        Cookie refreshTokenCookie = new Cookie("refreshToken", refreshToken);
        Cookie[] cookies = {refreshTokenCookie};

        when(request.getCookies()).thenReturn(cookies);

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

    // Helper class to mock HttpServletResponse.getOutputStream()

    static class TestServletOutputStream extends jakarta.servlet.ServletOutputStream {
        private final OutputStream outputStream;

        public TestServletOutputStream(OutputStream outputStream) {
            this.outputStream = outputStream;
        }

        @Override
        public void write(int b) throws IOException {
            outputStream.write(b);
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setWriteListener(WriteListener writeListener) {

        }
    }

}

