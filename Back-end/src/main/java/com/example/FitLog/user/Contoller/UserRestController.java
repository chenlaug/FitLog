package com.example.FitLog.user.Contoller;

import com.example.FitLog.user.DTO.UserDTO;
import com.example.FitLog.user.model.exception.UserCreationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import com.example.FitLog.user.service.UserService;

@RestController
public class UserRestController {
    private final UserService userService;
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("user/hello")
    public String hello() {
        return userService.sayHello();
    }

    @PostMapping("user/create")
    @ResponseStatus(HttpStatus.CREATED)
    public String create(@RequestBody UserDTO.PostInput input) throws UserCreationException {
        return userService.createUser(input.getName());
    }
}
