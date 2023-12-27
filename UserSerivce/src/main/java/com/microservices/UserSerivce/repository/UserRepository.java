package com.microservices.UserSerivce.repository;

import com.microservices.UserSerivce.entity.User;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    public Optional<User> findByUsername(String username);

    @Query("Select u From User u where u.username = ?1 or u.email = ?2")
    public Optional<User> findByUsernameOrEmail(String username, String email);
}
