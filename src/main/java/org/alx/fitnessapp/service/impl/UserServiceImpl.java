package org.alx.fitnessapp.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.alx.fitnessapp.converter.GoalDTOConverter;
import org.alx.fitnessapp.converter.UserDTOConverter;
import org.alx.fitnessapp.exception.EmailValidationException;
import org.alx.fitnessapp.exception.UserAlreadyExistsException;
import org.alx.fitnessapp.model.dto.GoalDTO;
import org.alx.fitnessapp.model.dto.UserDTO;
import org.alx.fitnessapp.model.entity.Goal;
import org.alx.fitnessapp.model.entity.User;
import org.alx.fitnessapp.repository.GoalRepository;
import org.alx.fitnessapp.repository.UserRepository;
import org.alx.fitnessapp.service.UserService;
import org.alx.fitnessapp.util.EmailValidation;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final GoalRepository goalRepository;
    private final UserDTOConverter userConverter;
    private final GoalDTOConverter goalConverter;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public String registerUser(UserDTO userDTO) throws UserAlreadyExistsException {
        if (userRepository.existsByUsername(userDTO.getUsername())) {
            throw new UserAlreadyExistsException("User already exists!");
        }

        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new UserAlreadyExistsException("Account with this email already exists");
        }

        try {
            userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));

            List<Goal> goals = goalRepository.saveAll(userDTO.getGoals().stream()
                    .map(goalConverter::convertGoalDTOToGoal)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList()));

            User user;

            if (EmailValidation.isValid(userDTO.getEmail())) {
                user = userConverter.convertUserDTOToUser(userDTO);

                user.setGoals(goals);
                user.setIsDeleted(false);

                userRepository.save(user);
            }
        } catch (EmailValidationException e) {
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
    public List<GoalDTO> getGoals(UserDTO userDTO) {
        List<Goal> goalsByUser = userRepository.findByUsername(userDTO.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User does not exist")).getGoals();


        return goalsByUser.stream().map(goalConverter::convertGoalToGoalDTO).collect(Collectors.toList());

    }

}

