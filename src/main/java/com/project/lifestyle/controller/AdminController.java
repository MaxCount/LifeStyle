package com.project.lifestyle.controller;

import com.project.lifestyle.model.User;
import com.project.lifestyle.repository.UserRepository;
import com.project.lifestyle.service.AdminService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin-page/")
@AllArgsConstructor
public class AdminController {

    private final AdminService adminService;
    private final UserRepository userRepository;

    @GetMapping("/users")
    private List<User> listUsers() {
        return adminService.listAll();
    }

    @PostMapping("/set-admin-role/{username}")
    private ResponseEntity<String> setAdminRole(@PathVariable String username){
        adminService.setAdminRole(username);
        return ResponseEntity.status(HttpStatus.OK).body("Admin role set successfully " + userRepository.findByUsername(username).get().getRoles());
    }


}
