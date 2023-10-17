package org.alx.fitnessapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.alx.fitnessapp.converter.GoalDTOConverter;
import org.alx.fitnessapp.model.dto.UserDTO;
import org.alx.fitnessapp.model.entity.Goal;
import org.alx.fitnessapp.model.entity.User;
import org.alx.fitnessapp.repository.GoalRepository;
import org.alx.fitnessapp.repository.UserRepository;
import org.alx.fitnessapp.service.GoalService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class GoalServiceImpl implements GoalService {
    private final UserRepository userRepository;
    private final GoalRepository goalRepository;
    private final GoalDTOConverter converter;

    @Override
    @Transactional
    public String updateGoalList(UserDTO userDTO) {
        User user = userRepository.findByUsername(userDTO.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User does not exist!"));

        List<Goal> goalsByUser = user.getGoals();

        goalsByUser.addAll(userDTO.getGoals().stream()
                .map(converter::convertGoalDTOToGoal)
                .filter(Objects::nonNull)
                .filter(goal -> !goalRepository.existsGoalByWeightGoalAndBodyTypeGoalAndWeeklyExercise(
                        goal.getWeightGoal(), goal.getBodyTypeGoal(), goal.getWeeklyExercise()))
                .toList());

        user.setGoals(goalsByUser);

        userRepository.save(user);

        return "Goal list updated successfully!";
    }

}
