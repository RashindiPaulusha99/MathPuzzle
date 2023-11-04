package com.uob.mathpuzzle.repository;

import com.uob.mathpuzzle.entity.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<Player,Long> {

    //get admin user details by email
    Optional<Player> findByEmail(String email);

}
