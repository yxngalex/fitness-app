package org.alx.fitnessapp.repository;

import org.alx.fitnessapp.model.entity.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealRepository extends JpaRepository<Meal, Integer> {

    Meal findMealByMealName(String mealName);
}
