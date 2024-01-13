package com.othmanfrdev.projecttrackerapi.service.impl;

import com.othmanfrdev.projecttrackerapi.dto.request.UserLoginRequest;
import com.othmanfrdev.projecttrackerapi.dto.response.UserLoginResponse;
import com.othmanfrdev.projecttrackerapi.dto.response.UserResponse;
import com.othmanfrdev.projecttrackerapi.repository.UserRepository;
import com.othmanfrdev.projecttrackerapi.security.JwtService;
import com.othmanfrdev.projecttrackerapi.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserDetailsService, UserService {
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;


    public UserServiceImpl(UserRepository userRepository,@Lazy AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return this.userRepository
                .findUserByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format("User with email %s not found.", email)));
    }

    @Override
    public UserLoginResponse login(UserLoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );
        var user = this.loadUserByUsername(request.getEmail());
        var jwtToken = jwtService.generateToken(user);
        return UserLoginResponse.builder()
                .accessToken(jwtToken)
                .build();
    }

    @Override
    public List<UserResponse> getUsers() {
        return this.userRepository
                .findAll()
                .stream()
                .map(UserResponse::from)
                .collect(Collectors.toList());
    }
}
