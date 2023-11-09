package org.alx.fitnessapp.controller;

import lombok.RequiredArgsConstructor;
import org.alx.fitnessapp.exception.InvalidAgeValidationException;
import org.alx.fitnessapp.exception.InvalidEmailValidationException;
import org.alx.fitnessapp.exception.UserAlreadyExistsException;
import org.alx.fitnessapp.model.dto.LoginResponse;
import org.alx.fitnessapp.model.dto.UserDTO;
import org.alx.fitnessapp.security.TokenProvider;
import org.alx.fitnessapp.service.UserService;
import org.alx.fitnessapp.validation.Validator;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final TokenProvider tokenProvider;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody UserDTO userDTO) {
        if (!Validator.isEmailValid(userDTO.getEmail())) {
            throw new InvalidEmailValidationException("Invalid Email!");
        }
        if (!Validator.isAgeValid(userDTO.getAge())) {
            throw new InvalidAgeValidationException("You need to be at least 18 years old!");
        }
        try {
            return ResponseEntity.ok(userService.registerUser(userDTO));
        } catch (UserAlreadyExistsException e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody UserDTO userDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userDTO.getUsername(), userDTO.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new LoginResponse(token));
    }
}
