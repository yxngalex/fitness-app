package org.alx.fitnessapp.repository;

import org.alx.fitnessapp.model.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Integer> {

//    @Query(value = "SELECT * FROM Exercise e JOIN Category c ON e.FK_CATEGORY_ID = c.CATEGORY_ID WHERE c.CATEGORY_NAME LIKE CONCAT('%', :categoryName, '%') ORDER BY RAND() LIMIT 5",
//            nativeQuery = true)
//    List<Exercise> getRandomExercisesByCategory(@Param("categoryName") String categoryName);

    List<Exercise> findAllByCategoryCategoryName(String category);
}
