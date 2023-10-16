package org.alx.fitnessapp.service;


import org.alx.fitnessapp.exception.UserAlreadyExistsException;
import org.alx.fitnessapp.model.dto.UserDTO;
import org.alx.fitnessapp.model.entity.User;

import java.util.Optional;

public interface UserService {

    String registerUser(UserDTO userDTO) throws UserAlreadyExistsException;

    UserDTO loginUser(UserDTO userDTO);
}
