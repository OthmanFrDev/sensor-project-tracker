package com.othmanfrdev.projecttrackerapi.dto.request;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserLoginRequest {
    private String email;
    private String password;
}
