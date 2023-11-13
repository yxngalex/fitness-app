package org.alx.fitnessapp.config;

import org.alx.fitnessapp.converter.UserDTOConverter;
import org.alx.fitnessapp.model.dto.GenderEnum;
import org.alx.fitnessapp.model.entity.User;
import org.alx.fitnessapp.repository.UserRepository;
import org.alx.fitnessapp.security.UserDetailsServiceImpl;
import org.alx.fitnessapp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

import javax.annotation.PostConstruct;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@Component
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ConfigBaseTest {

    @Autowired
    protected UserDTOConverter converter;

    @MockBean
    protected UserService userService;

    @MockBean
    protected UserRepository userRepository;

    protected UserDetailsService userDetailsService;


    @PostConstruct
    void setUserDetailsService() {
        userDetailsService = new UserDetailsServiceImpl(userRepository);
    }


    @BeforeEach()
    public void setup() {
        setLoggedUser();
    }

    private void setLoggedUser() {
        User mockUser = getUser();

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(mockUser));

        UserDetails userDetails = userDetailsService.loadUserByUsername(mockUser.getUsername());

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
