package com.example.reimbursementApi.controller;


import com.example.reimbursementApi.accessLog.JWTUtils;
import com.example.reimbursementApi.utils.LoginRequest;
import com.example.reimbursementApi.utils.LoginResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@CrossOrigin
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTUtils jwtUtils;

    @Autowired
    private UserDetailsService jwtUserDetailsService;



    private Logger logger = LoggerFactory.getLogger(AuthController.class);

    @PostMapping("login")
    public ResponseEntity<LoginResponse> generateToken(@RequestBody LoginRequest loginRequest) throws Exception {
        authenticate(loginRequest.getUsername(), loginRequest.getPassword());

        final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(loginRequest.getUsername());
        final String token = jwtUtils.generateToken(userDetails);

        return ResponseEntity.ok(new LoginResponse(false, token, null));
    }

    private void authenticate(@NonNull String username, @NonNull String password) throws Exception {
        System.out.println("Authentcating " + username);
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException de) {
            System.out.println("User not active");
        } catch (BadCredentialsException be) {
            throw new Exception("Invalid credentials", be);
        }
    }


}
