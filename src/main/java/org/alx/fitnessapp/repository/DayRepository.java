package org.alx.fitnessapp.repository;

import org.alx.fitnessapp.model.entity.Day;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface DayRepository extends JpaRepository<Day, Integer> {

    @Query("SELECT d FROM Day d JOIN User u ON d.user.id = u.id WHERE u.id = :id ORDER BY d.loggedDate DESC")
    List<Day> findAllByUserId(@Param("id") Integer id);

    Boolean existsByUserIdAndLoggedDate(Integer id, LocalDate date);

    Day findDayByUserIdAndLoggedDate(Integer id, LocalDate date);

}
