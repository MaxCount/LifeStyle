package com.project.lifestyle.repository;

import com.project.lifestyle.model.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);

    User findByToken(String token);

    @NotNull
    Optional<User> findById(@NotNull Long id);

    @Modifying
    @Query("delete from User u where u.userId = ?1  ")
    void deleteById(@NotNull Long id);
}
