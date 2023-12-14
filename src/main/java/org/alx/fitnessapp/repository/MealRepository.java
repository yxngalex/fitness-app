package org.alx.fitnessapp.repository;

import org.alx.fitnessapp.model.entity.Day;
import org.alx.fitnessapp.model.entity.Meal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MealRepository extends JpaRepository<Meal, Integer> {

    @Query("SELECT m From Meal m JOIN Day d ON m.Day.id = d.id WHERE m.mealName = :mealName AND d.user.username = :username")
    Meal findMealByMealName(@Param("mealName") String mealName, @Param("username") String username);

    @Query("SELECT m From Meal m JOIN Day d ON m.Day.id = d.id WHERE m.mealName = :mealName AND d = :day AND d.user.username = :username")
    Meal findMealByMealNameForDay(@Param("mealName") String mealName, @Param("day") Day day, @Param("username") String username);

    @Query("SELECT m From Meal m JOIN Day d ON m.Day.id = d.id WHERE d = :day AND m.mealName = :mealName")
    Meal findMealByDay(@Param("day") Day day, @Param("mealName") String mealName);

    @Query("SELECT m From Meal m JOIN Day d ON m.Day.id = d.id WHERE d.user.id = :userId")
    List<Meal> findAllMealsByUser(@Param("userId") Integer userId);

    @Query("SELECT m From Meal m JOIN Day d ON m.Day.id = d.id WHERE d = :day")
    List<Meal> findAllMealsByDay(Day day);
}
