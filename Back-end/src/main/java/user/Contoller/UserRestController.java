package user.Contoller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import user.service.UserService;

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

    @GetMapping("user/create")
    public String create() {
        return userService.createUser();
    }
}
