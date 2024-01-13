package com.othmanfrdev.projecttrackerapi.controller;

import com.othmanfrdev.projecttrackerapi.dto.request.UserLoginRequest;
import com.othmanfrdev.projecttrackerapi.dto.response.UserLoginResponse;
import com.othmanfrdev.projecttrackerapi.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/login")
@CrossOrigin
public class AuthenticationController {

    private final UserService userService;

    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserLoginResponse> login(@RequestBody UserLoginRequest userLoginRequest) {
        return new ResponseEntity<>(userService.login(userLoginRequest), HttpStatus.OK);
    }
}
