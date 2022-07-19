package com.project.lifestyle.service;

import com.project.lifestyle.dto.CommentDto;
import com.project.lifestyle.mapper.CommentMapper;
import com.project.lifestyle.model.Comment;
import com.project.lifestyle.model.Post;
import com.project.lifestyle.model.User;
import com.project.lifestyle.repository.CommentRepository;
import com.project.lifestyle.repository.PostRepository;
import com.project.lifestyle.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class CommentService {

    private static final String POST_URL = "";
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final AuthService authService;
    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final MailService mailService;

    public void save(CommentDto commentDto) {
        Post post = postRepository.findById(commentDto.getPostId())
                .orElseThrow(() -> new UsernameNotFoundException("Post with id = " + commentDto.getPostId().toString() + " not found"));
        Comment comment = commentMapper.map(commentDto, post, authService.getUser());
        commentRepository.save(comment);

        sendCommentNotification(authService.getUser());
    }

    private void sendCommentNotification(User user) {
        mailService.sendEmail(user.getEmail() , user.getUsername() + " posted a comment on your post." + POST_URL , user.getUsername() + " commented your post" + POST_URL);
    }

    public List<CommentDto> getAllCommentsForPost(Long postId) {
        Post post = postRepository.findById(postId).orElseThrow(() -> new UsernameNotFoundException("Post with id = " + postId + " not found"));
        return commentRepository.findByPost(post)
                .stream()
                .map(commentMapper::mapToDto).collect(toList());
    }

    public List<CommentDto> getAllCommentsForUser(String userName) {
        User user = userRepository.findByUsername(userName)
                .orElseThrow(() -> new UsernameNotFoundException(userName));
        return commentRepository.findAllByUser(user)
                .stream()
                .map(commentMapper::mapToDto)
                .collect(toList());
    }
}
