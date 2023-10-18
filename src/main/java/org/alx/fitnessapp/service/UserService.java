package org.alx.fitnessapp.service;


import org.alx.fitnessapp.exception.UserAlreadyExistsException;
import org.alx.fitnessapp.model.dto.GoalDTO;
import org.alx.fitnessapp.model.dto.UserDTO;
import org.alx.fitnessapp.model.entity.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface UserService {

    String registerUser(UserDTO userDTO) throws UserAlreadyExistsException;

    UserDTO getByUsername(String username);
    User getLoggedUser();
}
