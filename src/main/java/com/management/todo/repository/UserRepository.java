package com.management.todo.repository;

import com.management.todo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserName(String userName);

    Boolean existsByEmail(String email);

    Optional<User> findByUserNameOrEmail(String userName, String email);

    Boolean existsByUserName(String userName);

}
