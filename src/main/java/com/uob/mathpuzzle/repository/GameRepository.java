package com.uob.mathpuzzle.repository;


import com.uob.mathpuzzle.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface GameRepository extends JpaRepository<Player, Long> {

    //get admin user details by email
    Optional<Player> findByEmail(String email);

    @Query(value = "SELECT COUNT(id) FROM score WHERE player_id=:player_id",nativeQuery = true)
    Integer findByLevel(@Param("player_id") Long player_id);

    @Query(value = "SELECT SUM(score) FROM score WHERE player_id=:player_id",nativeQuery = true)
    Integer findByScores(@Param("player_id") Long player_id);



}
