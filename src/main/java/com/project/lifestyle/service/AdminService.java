package com.project.lifestyle.service;

import com.project.lifestyle.model.User;
import com.project.lifestyle.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AdminService {

    private final UserRepository userRepository;

    public List<User> listAll(){
        return userRepository.findAll();
    }
}
