package com.project.lifestyle.repository;

import com.project.lifestyle.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<User,Long> {
    Optional<User> findByToken(String token);


    ////////////////////////////////////////////////////////////
    @Modifying
    @Query("UPDATE User set token = token where token = ?1 ")
    void updateToken(String token);
    ////////////////////////////////////////////////////////////
}
