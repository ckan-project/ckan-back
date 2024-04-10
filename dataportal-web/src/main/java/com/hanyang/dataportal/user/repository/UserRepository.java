package com.hanyang.dataportal.user.repository;

import com.hanyang.dataportal.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    // @Query("SELECT u FROM User u WHERE u.email = :email and u.isActive = true")
    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN true ELSE false END FROM User u WHERE u.email = :email AND u.isActive = true")
    Optional<User> findByEmailAndActiveTrue(String email);
    @Query("SELECT u FROM User u WHERE u.name = :name and u.isActive = true")
    Optional<User> findByNameAndActiveTrue(String name);
    boolean existsByEmail(String email);
}
