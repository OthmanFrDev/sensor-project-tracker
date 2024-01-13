package com.othmanfrdev.projecttrackerapi.dto.response;

import com.othmanfrdev.projecttrackerapi.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
@AllArgsConstructor
public class UserResponse {
    private Long id;
    private String name;

    public static UserResponse from(User user){
        return UserResponse
                .builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }
}
