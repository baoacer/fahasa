package com.whilewework.fahasa.controller;

import com.whilewework.fahasa.dto.AuthenticationRequest;
import com.whilewework.fahasa.entity.User;
import com.whilewework.fahasa.enums.UserRole;
import com.whilewework.fahasa.repository.UserRepository;
import com.whilewework.fahasa.utils.JwtUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import jakarta.servlet.http.HttpServletResponse;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class AuthControllerTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserDetailsService userDetailsService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthController authController;

    @Mock
    private HttpServletResponse response;

    @BeforeEach
    public void setup() {
        // Mock behavior for authenticationManager
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(null);

        // Mock behavior for userDetailsService
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                "testuser", "password", null);
        when(userDetailsService.loadUserByUsername("testuser")).thenReturn(userDetails);

        // Mock behavior for userRepository
        User user = new User();
        user.setId(1L);
        user.setRole(UserRole.CUSTOMER);
        when(userRepository.findFirstByEmail("testuser")).thenReturn(Optional.of(user));

        // Mock behavior for jwtUtil
        when(jwtUtil.generateToken("testuser")).thenReturn("generated_jwt_token");
    }

    @Test
    public void testAuthenticateUser() throws Exception {
        // Mock input
        AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername("testuser");
        request.setPassword("password");

        // Perform the request
        authController.createAuthenticationToken(request, response);

        // Verify that authenticationManager.authenticate is called
        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    @Test
    public void testLoadUserDetails() throws Exception {
        // Mock input
        AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername("testuser");
        request.setPassword("password");

        // Perform the request
        authController.createAuthenticationToken(request, response);

        // Verify that userDetailsService.loadUserByUsername is called
        verify(userDetailsService, times(1)).loadUserByUsername("testuser");
    }

    @Test
    public void testGenerateJWT() throws Exception {
        // Mock input
        AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername("testuser");
        request.setPassword("password");

        // Perform the request
        authController.createAuthenticationToken(request, response);

        // Verify that jwtUtil.generateToken is called
        verify(jwtUtil, times(1)).generateToken("testuser");
    }

    @Test
    public void testWriteResponse() throws Exception {
        // Mock input
        AuthenticationRequest request = new AuthenticationRequest();
        request.setUsername("testuser");
        request.setPassword("password");

        // Perform the request
        authController.createAuthenticationToken(request, response);

        // Verify that response.getWriter().write is called with the expected JSON content
        verify(response.getWriter(), times(1)).write(anyString());
        verify(response, times(1)).addHeader("Authorization", "Bearer generated_jwt_token");
    }
}
