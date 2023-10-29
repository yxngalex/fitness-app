package org.alx.fitnessapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.alx.fitnessapp.converter.MealDTOConverter;
import org.alx.fitnessapp.model.dto.FoodDTO;
import org.alx.fitnessapp.model.entity.User;
import org.alx.fitnessapp.repository.MealRepository;
import org.alx.fitnessapp.service.DayService;
import org.alx.fitnessapp.service.MealService;
import org.alx.fitnessapp.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MealServiceImpl implements MealService {
    private final MealRepository mealRepository;
    private final MealDTOConverter converter;
    private final UserService userService;
    private final DayService dayService;

    @Override
    public String createMeal(List<FoodDTO> food) {
        User loggedUser = userService.getLoggedUser();

        return null;
    }

}
