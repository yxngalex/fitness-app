package org.alx.fitnessapp.service;


import org.alx.fitnessapp.exception.UserAlreadyExistsException;
import org.alx.fitnessapp.model.dto.GoalDTO;
import org.alx.fitnessapp.model.dto.UserDTO;

import java.util.List;

public interface UserService {

    String registerUser(UserDTO userDTO) throws UserAlreadyExistsException;

    UserDTO getByUsername(String username);

    List<GoalDTO> getGoals(UserDTO userDTO);
}
