package com.project.lifestyle.mapper;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.project.lifestyle.dto.PostRequest;
import com.project.lifestyle.dto.PostResponse;
import com.project.lifestyle.model.Post;
import com.project.lifestyle.model.User;
import com.project.lifestyle.model.Vote;
import com.project.lifestyle.model.VoteType;
import com.project.lifestyle.repository.CommentRepository;
import com.project.lifestyle.repository.VoteRepository;
import com.project.lifestyle.service.AuthService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static com.project.lifestyle.model.VoteType.DOWNVOTE;
import static com.project.lifestyle.model.VoteType.UPVOTE;

@Mapper(componentModel = "spring")
public abstract class PostMapper{

    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private VoteRepository voteRepository;
    @Autowired
    private AuthService authService;

    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", source = "postRequest.description")
    @Mapping(target = "voteCount", constant = "0")
    @Mapping(target = "user", source = "user")
    public abstract Post map(PostRequest postRequest, User user);

    @Mapping(target = "id", source = "postId")
    @Mapping(target = "userName", source = "user.username")
    @Mapping(target = "commentCount", expression = "java(commentCount(post))")
    @Mapping(target = "duration", expression = "java(getDuration(post))")
    @Mapping(target = "upVote", expression = "java(isPostUpVoted(post))")
    @Mapping(target = "downVote", expression = "java(isPostDownVoted(post))")
    public abstract PostResponse mapToDto(Post post);

    String getDuration(Post post) {
        return TimeAgo.using(post.getCreatedDate().toEpochMilli());
    }

    Integer commentCount(Post post) {
        return commentRepository.findByPost(post).size();
    }

    boolean isPostUpVoted(Post post) {
        return checkVoteType(post, UPVOTE);
    }

    boolean isPostDownVoted(Post post) {
        return checkVoteType(post, DOWNVOTE);
    }

    private boolean checkVoteType(Post post, VoteType voteType) {
        if (authService.isLoggedIn()) {
            Optional<Vote> voteForPostByUser =
                    voteRepository.findTopByPostAndUserOrderByVoteIdDesc(post,
                            authService.getUser());
            return voteForPostByUser.filter(vote -> vote.getVoteType().equals(voteType))
                    .isPresent();
        }
        return false;
    }
}