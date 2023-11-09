package org.alx.fitnessapp.service;


import org.alx.fitnessapp.exception.UserAlreadyExistsException;
import org.alx.fitnessapp.model.dto.UserDTO;
import org.alx.fitnessapp.model.entity.User;

public interface UserService {

    String registerUser(UserDTO userDTO) throws UserAlreadyExistsException;

    UserDTO getByUsername(String username);
    User getLoggedUser();
}
