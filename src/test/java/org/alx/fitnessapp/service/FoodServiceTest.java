package org.alx.fitnessapp.service;

import org.alx.fitnessapp.config.ConfigBaseTest;
import org.alx.fitnessapp.model.dto.FoodDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class FoodServiceTest extends ConfigBaseTest {

    @Autowired
    private FoodService foodService;

    @Test
    void getAllFoods() {
        List<FoodDTO> foods = foodService.getAllFoods();

        assertFalse(foods.isEmpty());
        assertEquals(14, foods.size());
    }

    @Test
    void getFoodAutoComplete() {
        List<FoodDTO> foodsByNameOne = foodService.getFoodAutoComplete("Milk");
        List<FoodDTO> foodsByNameTwo = foodService.getFoodAutoComplete("Apple");
        List<FoodDTO> foodsByNameThree = foodService.getFoodAutoComplete("Tuna");

        assertFalse(foodsByNameOne.isEmpty());
        assertEquals(1, foodsByNameOne.size());
        assertFalse(foodsByNameTwo.isEmpty());
        assertEquals(1, foodsByNameTwo.size());
        assertFalse(foodsByNameThree.isEmpty());
        assertEquals(1, foodsByNameThree.size());
    }

}
