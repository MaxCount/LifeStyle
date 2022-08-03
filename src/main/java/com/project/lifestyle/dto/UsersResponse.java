package com.project.lifestyle.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UsersResponse {
    private Long userId;
    private String username;
    private String email;
    private boolean enabled;
    private String token;
    private String authorities;
}
