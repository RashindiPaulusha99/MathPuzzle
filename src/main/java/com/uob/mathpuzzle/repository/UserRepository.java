package com.uob.mathpuzzle.repository;

import com.uob.mathpuzzle.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface UserRepository extends JpaRepository<User,Long> {

    //get admin user details by email
    User findByEmail(String email);

}
