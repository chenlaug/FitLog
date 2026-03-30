package com.example.FitLog.user.persistence;


import org.springframework.data.jpa.repository.JpaRepository;
import com.example.FitLog.user.model.UserEntity;

import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, UUID> {
}
