package org.alx.fitnessapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alx.fitnessapp.converter.GoalDTOConverter;
import org.alx.fitnessapp.converter.UserDTOConverter;
import org.alx.fitnessapp.exception.InvalidAgeValidationExceptionAbstract;
import org.alx.fitnessapp.exception.InvalidEmailValidationExceptionAbstract;
import org.alx.fitnessapp.exception.UserAlreadyExistsExceptionAbstract;
import org.alx.fitnessapp.model.dto.UserDTO;
import org.alx.fitnessapp.model.entity.Goal;
import org.alx.fitnessapp.model.entity.User;
import org.alx.fitnessapp.repository.GoalRepository;
import org.alx.fitnessapp.repository.UserRepository;
import org.alx.fitnessapp.service.UserService;
import org.alx.fitnessapp.validation.Validator;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final GoalRepository goalRepository;
    private final UserDTOConverter userConverter;
    private final GoalDTOConverter goalConverter;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public String registerUser(UserDTO userDTO) throws UserAlreadyExistsExceptionAbstract {
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new UserAlreadyExistsExceptionAbstract("User already exists!");
        }

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new UserAlreadyExistsExceptionAbstract("Account with this email already exists");
        }

        try {
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));

            User user;

            Goal goal = goalConverter.convertGoalDTOToGoal(userDTO.getGoal());

            if (goal != null)
                goalRepository.save(goal);

            if (Validator.isEmailValid(userDTO.getEmail()) && Validator.isAgeValid(userDTO.getAge())) {
                user = userConverter.convertUserDTOToUser(userDTO);

                user.setIsDeleted(false);
                user.setGoal(goal);

                userRepository.save(user);
            }
        } catch (InvalidEmailValidationExceptionAbstract | InvalidAgeValidationExceptionAbstract e) {
            log.error(e.getMessage(), e);
            return e.getMessage();
        }

        return "User registered successfully!";
    }

    @Override
    public UserDTO getByUsername(String username) {
        return userConverter.convertUserToUserDTO(userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User not found!")
        ));
    }

    @Override
    public User getLoggedUser() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        return userRepository.findByUsername(userDetails.getUsername()).orElseThrow(() -> new UsernameNotFoundException("User does not exist"));
    }

}

