package com.project.lifestyle.repository;

import com.project.lifestyle.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface RoleRepository extends JpaRepository<Role, Long> {
//    @Query("SELECT r from Role r where r.name = ?1")
   public Role findByName(String name);
}
