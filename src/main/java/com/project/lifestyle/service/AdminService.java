package com.project.lifestyle.service;

import com.project.lifestyle.dto.UsersResponse;
import com.project.lifestyle.model.Role;
import com.project.lifestyle.model.User;
import com.project.lifestyle.repository.PostRepository;
import com.project.lifestyle.repository.RoleRepository;
import com.project.lifestyle.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final AuthService authService;
    private final RoleRepository roleRepository;
    private final PostRepository postRepository;

    public List<UsersResponse> userModel(){
        List<UsersResponse> arr = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            arr.add(UsersResponse.builder()
                 .userId(user.getUserId())
                 .username(user.getUsername())
                 .email(user.getEmail())
                 .enabled(user.isEnabled())
                 .token(user.getToken())
                 .authorities(user.getAuthorities().toString())
                 .build()) ;
        }
        return arr;
    }
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
    @Transactional
    public void deletePost(String postName){
        postRepository.deleteByName(postName);
    }

}
