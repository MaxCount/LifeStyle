package com.project.lifestyle.service;

import com.project.lifestyle.model.Role;
import com.project.lifestyle.model.User;
import com.project.lifestyle.repository.PostRepository;
import com.project.lifestyle.repository.RoleRepository;
import com.project.lifestyle.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final AuthService authService;
    private final RoleRepository roleRepository;
    private final PostRepository postRepository;

    public List<User> listAll(){
        return userRepository.findAll();
    }
    public void setAdminRole(String username){
        Role roleUser = roleRepository.findByName("ADMIN");
        User user = authService.getUser(username);
        user.addRole(roleUser);

        userRepository.save(user);
    }
    public void deleteUser(Long id){
        userRepository.deleteById(id);
    }
    public void deletePost(Long id){
        postRepository.deleteById(id);
    }

}
