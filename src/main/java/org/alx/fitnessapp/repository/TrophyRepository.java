package org.alx.fitnessapp.repository;

import org.alx.fitnessapp.model.entity.Trophy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrophyRepository extends JpaRepository<Trophy, Integer> {

    Trophy findTrophyByTrophyName(String trophyName);
}
