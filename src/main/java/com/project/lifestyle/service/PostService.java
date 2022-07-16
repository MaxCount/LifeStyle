package com.project.lifestyle.service;

import com.project.lifestyle.dto.PostRequest;
import com.project.lifestyle.mapper.PostMapper;
import com.project.lifestyle.model.User;
import com.project.lifestyle.repository.PostRepository;
import com.project.lifestyle.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.security.oauth2.jwt.Jwt;

@Service
@AllArgsConstructor
@Slf4j
@Transactional
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;

    public void save(PostRequest postRequest) {
        postRepository.save(postMapper.map(postRequest, getUser()));
    }

//    private String getUsername(){
//
//        return ;
//    }

    @Transactional(readOnly = true)
    public User getUser() {
        Jwt principal = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getSubject())
                .orElseThrow(() -> new UsernameNotFoundException("User name not found - " + principal.getSubject()));
    }
}
