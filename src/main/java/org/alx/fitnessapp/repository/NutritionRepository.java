package org.alx.fitnessapp.repository;

import org.alx.fitnessapp.model.entity.Nutrition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface NutritionRepository extends JpaRepository<Nutrition, Integer> {

    @Query("SELECT n FROM Meal m JOIN Nutrition n ON m.nutrition.id = n.id")
    Nutrition findNutritionForMeal(@Param("mealName") String mealName);
}
