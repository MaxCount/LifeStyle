package com.project.lifestyle.repository;

import com.project.lifestyle.model.Categories;
import com.project.lifestyle.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoriesRepository extends JpaRepository<Categories, Long> {

 List<Categories> findAllByUser(User userid);
}
