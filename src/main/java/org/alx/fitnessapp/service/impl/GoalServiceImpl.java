package org.alx.fitnessapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.alx.fitnessapp.converter.GoalDTOConverter;
import org.alx.fitnessapp.converter.UserDTOConverter;
import org.alx.fitnessapp.model.dto.GoalDTO;
import org.alx.fitnessapp.model.dto.UserDTO;
import org.alx.fitnessapp.model.entity.Goal;
import org.alx.fitnessapp.model.entity.User;
import org.alx.fitnessapp.repository.GoalRepository;
import org.alx.fitnessapp.repository.UserRepository;
import org.alx.fitnessapp.service.GoalService;
import org.alx.fitnessapp.service.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GoalServiceImpl implements GoalService {
    private final UserRepository userRepository;
    private final GoalRepository goalRepository;
    private final GoalDTOConverter converter;
    private final UserDTOConverter userConverter;
    private final UserService userService;

    @Override
    @Transactional
    public String createGoal(GoalDTO goalDTO) {
        User user = userService.getLoggedUser();

        user.setGoal(converter.convertGoalDTOToGoal(goalDTO));

        userRepository.save(user);
        return "Goal list updated successfully!";
    }

    @Override
    public GoalDTO getGoal() {
        User loggedUser = userService.getLoggedUser();
        UserDTO userDTO = userConverter.convertUserToUserDTO(loggedUser);

        return userDTO.getGoal();
    }

    @Override
    public GoalDTO updateGoal(GoalDTO goalDTO) {
        User loggedUser = userService.getLoggedUser();
        Goal g = loggedUser.getGoal();

        g.setWeightGoal(goalDTO.getWeightGoal());
        g.setBodyTypeGoal(goalDTO.getBodyTypeGoal());
        g.setWeeklyExercise(goalDTO.getWeeklyExercise());

        goalRepository.save(g);

        return converter.convertGoalToGoalDTO(g);
    }

    @Override
    public String deleteGoal(Integer goalId) {
        goalRepository.deleteById(goalId);

        return "Goal Deleted Successfully";
    }

}
