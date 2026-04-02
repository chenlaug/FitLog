package com.example.FitLog.user.controller;

import com.example.FitLog.user.DTO.UserDTO;
import com.example.FitLog.user.mapper.UserMapper;
import com.example.FitLog.user.model.UserEntity;
import com.example.FitLog.user.model.exception.UserCreationException;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.example.FitLog.user.service.UserService;

@RestController
@RequestMapping("/user")
public class UserRestController {
    private final UserService userService;
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@Valid @RequestBody UserDTO.PostInput input) throws UserCreationException {
        UserEntity newUser = userService.createUser(input.getName(),input.getEmail(),input.getPassword());
        UserDTO.PostOutput output = UserMapper.toPostOutput(newUser);
        return output.toString();
    }
}
