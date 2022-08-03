package com.project.lifestyle.controller;

import com.project.lifestyle.dto.UsersResponse;
import com.project.lifestyle.model.User;
import com.project.lifestyle.repository.UserRepository;
import com.project.lifestyle.service.AdminService;
import com.project.lifestyle.service.AuthService;
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
    private final AuthService authService;

    @GetMapping("/users")
    private List<User> listUsers() {
        return adminService.listAll();
    }
    @PostMapping("/set-admin-role/{username}")
    private ResponseEntity<String> setAdminRole(@PathVariable String username){
        adminService.setAdminRole(username);
        return ResponseEntity.status(HttpStatus.OK).body("Admin role set successfully " + userRepository.findByUsername(username).get().getRoles());
    }
    @DeleteMapping("/deleteUser/{id}")
    private ResponseEntity<String> deleteUser(@PathVariable Long id){
        adminService.deleteUser(id);
        return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully");
    }
    @DeleteMapping("/deletePost/{username}")
    private ResponseEntity<String>deletePost(@PathVariable String username){
        adminService.deletePost(username);
        return ResponseEntity.status(HttpStatus.OK).body("Post deleted successfully");
    }
    @GetMapping("/isAdmin/{username}")
    private boolean isAdmin(@PathVariable String username){
       return authService.isAdmin(username);
    }
    @GetMapping("/getUserByUsername/{username}")
    public User getUserByUsername(@PathVariable String username){
        return authService.getUser(username);
    }
    @GetMapping("/getUsers")
    private List<UsersResponse> usersModel(){
        return adminService.userModel();
    }
}
