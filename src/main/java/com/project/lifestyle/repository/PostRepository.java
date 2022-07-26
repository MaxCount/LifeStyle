package com.project.lifestyle.repository;

import com.project.lifestyle.model.Post;
import com.project.lifestyle.model.User;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUser(User user);

    @Modifying
    @Query("delete from Post p where p.postId = ?1  ")
    void deleteById(@NotNull Long id);
}
