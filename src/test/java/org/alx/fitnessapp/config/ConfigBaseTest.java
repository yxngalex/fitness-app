package org.alx.fitnessapp.config;

import org.alx.fitnessapp.model.dto.GenderEnum;
import org.alx.fitnessapp.model.entity.Goal;
import org.alx.fitnessapp.model.entity.User;
import org.alx.fitnessapp.repository.GoalRepository;
import org.alx.fitnessapp.repository.UserRepository;
import org.alx.fitnessapp.security.UserDetailsServiceImpl;
import org.alx.fitnessapp.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@Component
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@SpringBootTest
@ComponentScan(basePackages = "org.alx.fitnessapp")
public class ConfigBaseTest {

    @MockBean
    protected UserRepository userRepository;
    @MockBean
    protected GoalRepository goalRepository;

    @MockBean
    protected UserService userService;
    protected UserDetailsService userDetailsService;

    @BeforeEach()
    public void setup() {
        userDetailsService = new UserDetailsServiceImpl(userRepository);
        User mockUser = getUser();
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(mockUser));
        when(userService.getLoggedUser()).thenReturn(mockUser);
        when(goalRepository.save(any())).thenReturn(new Goal());
        when(userRepository.save(any())).thenReturn(new User());
        setAuthentication(mockUser);
    }

    @AfterEach()
    public void cleanup() {
        SecurityContextHolder.clearContext();
    }

    private void setAuthentication(User user) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    private static User getUser() {
        User mockUser = new User();
        mockUser.setId(1);
        mockUser.setUsername("userTest");
        mockUser.setPassword("userTest");
        mockUser.setEmail("userTest@mail.com");
        mockUser.setAge(21);
        mockUser.setWeight(68.8);
        mockUser.setHeight(180);
        mockUser.setGender(GenderEnum.MAN.name());

        return mockUser;
    }
}
