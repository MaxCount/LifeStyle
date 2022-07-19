package com.project.lifestyle.mapper;

import com.github.marlonlom.utilities.timeago.TimeAgo;
import com.project.lifestyle.dto.PostRequest;
import com.project.lifestyle.dto.PostResponse;
import com.project.lifestyle.model.Post;
import com.project.lifestyle.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public abstract class PostMapper{

    @Mapping(target = "createdDate", expression = "java(java.time.Instant.now())")
    @Mapping(target = "description", source = "postRequest.description")
    @Mapping(target = "user", source = "user")
    public abstract Post map(PostRequest postRequest, User user);

    @Mapping(target = "id", source = "postId")
    @Mapping(target = "userName", source = "user.username")
    @Mapping(target = "duration", expression = "java(getDuration(post))")
    public abstract PostResponse mapToDto(Post post);

    String getDuration(Post post) {
        return TimeAgo.using(post.getCreatedDate().toEpochMilli());
    }

}