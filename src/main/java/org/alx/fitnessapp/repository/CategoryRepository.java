package org.alx.fitnessapp.repository;

import org.alx.fitnessapp.model.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    @Query("SELECT c FROM Category c WHERE c.categoryName LIKE 'LEGS' OR c.categoryName LIKE 'PUSH' OR c.categoryName LIKE 'PULL'")
    List<Category> findCategoryForWorkoutRoutine();
}
