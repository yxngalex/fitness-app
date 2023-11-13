package org.alx.fitnessapp.config;

import org.alx.fitnessapp.converter.UserDTOConverter;
import org.alx.fitnessapp.model.dto.BodyTypeGoalEnum;
import org.alx.fitnessapp.model.dto.GenderEnum;
import org.alx.fitnessapp.model.entity.Goal;
import org.alx.fitnessapp.model.entity.User;
import org.alx.fitnessapp.repository.UserRepository;
import org.alx.fitnessapp.security.UserDetailsServiceImpl;
import org.alx.fitnessapp.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

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
    protected UserDetailsServiceImpl userDetailsService;

    User mockedLoggedUser;

    @Mock
    protected UserRepository userRepository;

    @BeforeEach()
    public void setup() {
        setLoggedUser();
    }

    private void setLoggedUser() {
        User mockUser = getUser();

        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(mockUser));

//        when(userService.getByUsername(anyString())).thenReturn(converter.convertUserToUserDTO(mockUser));
//        Authentication auth = mock(Authentication.class);
//        when(auth.getPrincipal()).thenReturn(mockUser);

//        UserDetails userDetails = new org.springframework.security.core.userdetails.User(mockUser.getUsername(), mockUser.getPassword(), Collections.emptyList());

//        Authentication auth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//        when(userDetailsService.loadUserByUsername("userTest")).thenReturn(userDetails);

//        SecurityContext securityContext = mock(SecurityContext.class);
//        when(securityContext.getAuthentication()).thenReturn(auth);

//        SecurityContextHolder.setContext(securityContext);

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
