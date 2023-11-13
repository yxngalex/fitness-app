package org.alx.fitnessapp.repository;

import org.alx.fitnessapp.model.entity.Trophy;
import org.alx.fitnessapp.model.entity.TrophyUser;
import org.alx.fitnessapp.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrophyUserRepository extends JpaRepository<TrophyUser, Integer> {

    TrophyUser findTrophyUserByTrophyAndUser(Trophy trophy, User user);

}
