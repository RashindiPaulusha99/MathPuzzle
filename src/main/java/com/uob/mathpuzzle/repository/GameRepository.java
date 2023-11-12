package com.uob.mathpuzzle.repository;


import com.uob.mathpuzzle.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface GameRepository extends JpaRepository<Score, Long> {

    @Query(value = "SELECT COUNT(id) FROM score WHERE player_id=:player_id",nativeQuery = true)
    Integer findByLevel(@Param("player_id") Long player_id);

    @Query(value = "SELECT SUM(score) FROM score WHERE player_id=:player_id",nativeQuery = true)
    Integer findByScores(@Param("player_id") Long player_id);

    @Query(value = "SELECT * FROM score WHERE player_id=:player_id",nativeQuery = true)
    Score findByPlayer(@Param("player_id") Long player_id);



}
