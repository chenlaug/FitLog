package user.service;

import org.springframework.stereotype.Service;
import user.persistence.UserRepository;
import user.model.UserEntity;

@Service
public class UserService {
    private final UserRepository userRepository;
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String sayHello() {
        return "Hello World!";
    }

    public String createUser() {
        UserEntity user = UserEntity
                .builder()
                .name("laughan2").build();

        userRepository.save(user);
        return  user.getName();
    }
}
