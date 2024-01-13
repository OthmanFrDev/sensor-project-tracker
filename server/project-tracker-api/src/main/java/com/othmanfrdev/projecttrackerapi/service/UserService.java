package com.othmanfrdev.projecttrackerapi.service;

import com.othmanfrdev.projecttrackerapi.dto.request.UserLoginRequest;
import com.othmanfrdev.projecttrackerapi.dto.response.UserLoginResponse;
import com.othmanfrdev.projecttrackerapi.dto.response.UserResponse;
import com.othmanfrdev.projecttrackerapi.entity.User;

import java.util.List;

public interface UserService {
    UserLoginResponse login(UserLoginRequest request);
    List<UserResponse> getUsers();

}
