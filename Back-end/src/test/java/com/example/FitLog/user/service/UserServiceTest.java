package com.example.FitLog.user.service;

import com.example.FitLog.auth.DTO.AuthDTO;
import com.example.FitLog.user.DTO.UserDTO;
import com.example.FitLog.user.model.UserEntity;
import com.example.FitLog.user.model.exception.UserException;
import com.example.FitLog.user.persistence.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private UUID userId;
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        userEntity = UserEntity.builder()
                .id(userId)
                .name("John")
                .email("john@example.com")
                .password("encodedPassword")
                .build();
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    // ---- Helper ----

    private void mockSecurityContext(UUID id) {
        Authentication auth = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(auth);
        when(auth.getPrincipal()).thenReturn(id);
        SecurityContextHolder.setContext(securityContext);
    }

    // ---- createUser ----

    @Test
    void createUser_shouldReturnOk_whenValidInput() {
        when(userRepository.existsByEmail("john@example.com")).thenReturn(false);
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");

        ResponseEntity<AuthDTO.RegisterOutput> response =
                userService.createUser("John", "john@example.com", "password");

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    void createUser_shouldThrowConflict_whenEmailAlreadyExists() {
        when(userRepository.existsByEmail("john@example.com")).thenReturn(true);

        assertThatThrownBy(() -> userService.createUser("John", "john@example.com", "password"))
                .isInstanceOf(UserException.class)
                .hasMessage("User already exists");
    }

    @Test
    void createUser_shouldThrowBadRequest_whenNameIsBlank() {
        when(userRepository.existsByEmail("john@example.com")).thenReturn(false);

        assertThatThrownBy(() -> userService.createUser("", "john@example.com", "password"))
                .isInstanceOf(UserException.class)
                .hasMessageContaining("Name cannot be empty");
    }

    @Test
    void createUser_shouldThrowBadRequest_whenEmailIsBlank() {
        when(userRepository.existsByEmail("")).thenReturn(false);

        assertThatThrownBy(() -> userService.createUser("John", "", "password"))
                .isInstanceOf(UserException.class)
                .hasMessageContaining("Email cannot be empty");
    }

    // ---- getMe ----

    @Test
    void getMe_shouldReturnUser_whenAuthenticated() {
        mockSecurityContext(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));

        ResponseEntity<UserDTO.GetOutput> response = userService.getMe();

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getEmail()).isEqualTo("john@example.com");
    }

    @Test
    void getMe_shouldThrowNotFound_whenUserDoesNotExist() {
        mockSecurityContext(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> userService.getMe())
                .isInstanceOf(UserException.class)
                .hasMessage("User not found");
    }

    // ---- deleteById ----

    @Test
    void deleteById_shouldReturnOk_whenUserExists() {
        mockSecurityContext(userId);
        when(userRepository.existsById(userId)).thenReturn(true);

        ResponseEntity<UserDTO.DeleteOutput> response = userService.deleteById();

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        verify(userRepository).deleteById(userId);
    }

    @Test
    void deleteById_shouldThrowNotFound_whenUserDoesNotExist() {
        mockSecurityContext(userId);
        when(userRepository.existsById(userId)).thenReturn(false);

        assertThatThrownBy(() -> userService.deleteById())
                .isInstanceOf(UserException.class)
                .hasMessage("User not found");
    }

    // ---- updateById ----

    @Test
    void updateById_shouldReturnOk_whenValidInput() {
        mockSecurityContext(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));

        ResponseEntity<UserDTO.PathOutput> response = userService.updateById("NewName", "new@example.com");

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(userEntity.getName()).isEqualTo("NewName");
        assertThat(userEntity.getEmail()).isEqualTo("new@example.com");
        verify(userRepository).save(userEntity);
    }

    @Test
    void updateById_shouldKeepExistingValues_whenFieldsAreNull() {
        mockSecurityContext(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(userEntity));

        userService.updateById(null, null);

        assertThat(userEntity.getName()).isEqualTo("John");
        assertThat(userEntity.getEmail()).isEqualTo("john@example.com");
    }
}
