package org.alx.fitnessapp.service;


import org.alx.fitnessapp.exception.UserAlreadyExistsExceptionAbstract;
import org.alx.fitnessapp.model.dto.UserDTO;
import org.alx.fitnessapp.model.entity.User;

public interface UserService {

    String registerUser(UserDTO userDTO) throws UserAlreadyExistsExceptionAbstract;

    UserDTO getByUsername(String username);
    User getLoggedUser();
}
