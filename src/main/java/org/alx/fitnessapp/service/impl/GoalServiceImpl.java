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
    private final UserService userService;

    @Override
    public String createOrUpdateGoal(GoalDTO goalDTO) {
        User user = userService.getLoggedUser();

        if (user.getGoal() == null) {
            Goal g = converter.convertGoalDTOToGoal(goalDTO);

            goalRepository.save(g);

            user.setGoal(g);
            userRepository.save(user);

            return "Goal created successfully!";
        } else {
            Goal g = user.getGoal();

            g.setWeightGoal(goalDTO.getWeightGoal());
            g.setWeeklyExercise(goalDTO.getWeeklyExercise());
            g.setBodyTypeGoal(goalDTO.getBodyTypeGoal());

            user.setGoal(g);
            userRepository.save(user);
            return "Goal updated successfully!";
        }
    }

    @Override
    public GoalDTO getGoal() {
        User loggedUser = userService.getLoggedUser();
        return converter.convertGoalToGoalDTO(loggedUser.getGoal());
    }

    @Override
    public String deleteGoal(Integer goalId) {
        goalRepository.deleteById(goalId);

        return "Goal Deleted Successfully";
    }

}
