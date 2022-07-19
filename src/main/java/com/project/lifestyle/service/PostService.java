package com.project.lifestyle.service;

import com.project.lifestyle.dto.PostRequest;
import com.project.lifestyle.dto.PostResponse;
import com.project.lifestyle.mapper.PostMapper;
import com.project.lifestyle.model.Post;
import com.project.lifestyle.model.User;
import com.project.lifestyle.repository.PostRepository;
import com.project.lifestyle.repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

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

    @Transactional(readOnly = true)
    public List<PostResponse> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public PostResponse getPost(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("Post with id = " + id + "not found"));
        return postMapper.mapToDto(post);
    }

    @Transactional(readOnly = true)
    public List<PostResponse> getPostsByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
        return postRepository.findByUser(user)
                .stream()
                .map(postMapper::mapToDto)
                .collect(toList());
    }

    @Transactional(readOnly = true)
    public User getUser() {
        User principal = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userRepository.findByUsername(principal.getUsername())
                .orElseThrow(() -> new UsernameNotFoundException("User with name - " + principal.getUsername() + " not found"));
    }


}
