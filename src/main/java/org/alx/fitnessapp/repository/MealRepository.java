package org.alx.fitnessapp.repository;

import org.alx.fitnessapp.model.entity.Day;
import org.alx.fitnessapp.model.entity.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MealRepository extends JpaRepository<Meal, Integer> {

    @Query("SELECT m From Meal m JOIN Day d ON m.Day.id = d.id WHERE m.mealName = :mealName AND d.user.username = :username")
    Meal findMealByMealName(@Param("mealName") String mealName,@Param("username") String username);

    @Query("SELECT m From Meal m JOIN Day d ON m.Day.id = d.id WHERE d = :day")
    Meal findMealByDay(@Param("day") Day day);
}
