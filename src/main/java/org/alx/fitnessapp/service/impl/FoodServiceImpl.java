package org.alx.fitnessapp.service.impl;

import lombok.RequiredArgsConstructor;
import org.alx.fitnessapp.converter.FoodDTOConverter;
import org.alx.fitnessapp.model.dto.FoodDTO;
import org.alx.fitnessapp.model.entity.Food;
import org.alx.fitnessapp.repository.FoodRepository;
import org.alx.fitnessapp.service.FoodService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoodServiceImpl implements FoodService {
    private final FoodRepository foodRepository;
    private final FoodDTOConverter converter;

    @Override
    public List<FoodDTO> getAllFoods() {
        List<Food> foodList = foodRepository.findAll();
        List<FoodDTO> foodDTOS = new ArrayList<>();

        for (Food food : foodList) {
            FoodDTO dto = converter.convertFoodToFoodDTO(food);
            foodDTOS.add(dto);
        }

        return foodDTOS;
    }

    @Override
    public List<FoodDTO> getFoodAutoComplete(String foodName) {
        List<FoodDTO> foodList = getAllFoods();

        return foodList.stream()
                .filter(f -> f.getFoodName().toLowerCase(Locale.ROOT).startsWith(foodName.toLowerCase(Locale.ROOT)))
                .collect(Collectors.toList());
    }


}
