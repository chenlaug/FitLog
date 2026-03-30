package user.persistence;


import org.springframework.data.jpa.repository.JpaRepository;
import user.model.UserEntity;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
}
