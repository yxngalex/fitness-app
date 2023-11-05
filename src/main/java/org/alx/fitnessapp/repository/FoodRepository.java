package org.alx.fitnessapp.repository;

import org.alx.fitnessapp.model.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food, Integer> {
    Food findFoodByFoodName(String name);

    List<Food> findAllByMealListId(Integer mealId);
}
