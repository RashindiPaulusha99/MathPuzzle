package com.uob.mathpuzzle.repository;


import com.uob.mathpuzzle.dto.projection.RankDTO;
import com.uob.mathpuzzle.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface GameRepository extends JpaRepository<Score, Long> {

    @Query(value = "SELECT COUNT(id) FROM score WHERE player_id=:player_id",nativeQuery = true)
    Integer findByLevel(@Param("player_id") Long player_id);

    @Query(value = "SELECT SUM(score) FROM score WHERE player_id=:player_id",nativeQuery = true)
    Integer findByScores(@Param("player_id") Long player_id);

    @Query(value = "SELECT SUM(reward) FROM score WHERE player_id=:player_id",nativeQuery = true)
    Integer findByRewards(@Param("player_id") Long player_id);

    @Query(value = "SELECT * FROM score WHERE player_id=:player_id",nativeQuery = true)
    Score findByPlayer(@Param("player_id") Long player_id);

    @Query(value = "SELECT player_id, SUM(score) AS score, SUM(reward) AS reward, RANK() OVER (ORDER BY SUM(score) DESC) AS ranks FROM score GROUP BY player_id",nativeQuery = true)
    List<RankDTO> findAllScoreAndRewardPerPlayer();

}
