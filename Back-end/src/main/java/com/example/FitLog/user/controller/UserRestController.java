package com.example.FitLog.user.controller;

import com.example.FitLog.user.DTO.UserDTO;

import com.example.FitLog.user.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@Slf4j
@RestController
@RequestMapping("/user")
public class UserRestController {

    private final UserService userService;

    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserDTO.GetOutput> getMe() {
        return userService.getMe();
    }

    @DeleteMapping("")
    public ResponseEntity<UserDTO.DeleteOutput> deleteMe() {
        return userService.deleteById();
    }

    @PatchMapping("")
    public ResponseEntity<UserDTO.PathOutput> updateUser(@Valid @RequestBody UserDTO.PatchInput input) {
        return userService.updateById(input.getName(), input.getEmail());
    }

}
