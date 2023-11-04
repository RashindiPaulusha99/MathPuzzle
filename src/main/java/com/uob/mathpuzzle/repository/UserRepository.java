package com.uob.mathpuzzle.repository;

import com.uob.mathpuzzle.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {

    //get admin user details by email
    Optional<User> findByEmail(String email);

}
