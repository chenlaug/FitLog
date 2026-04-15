package com.example.FitLog.workout.service;

import com.example.FitLog.exercise.model.ExerciseEntity;
import com.example.FitLog.exercise.model.exception.ExerciseException;
import com.example.FitLog.exercise.persistence.ExerciseRepository;
import com.example.FitLog.user.model.UserEntity;
import com.example.FitLog.user.persistence.UserRepository;
import com.example.FitLog.workout.DTO.WorkoutDTO;
import com.example.FitLog.workout.model.WorkoutEntity;
import com.example.FitLog.workout.model.WorkoutExerciseEntity;
import com.example.FitLog.workout.model.exception.WorkoutException;
import com.example.FitLog.workout.persistence.WorkoutExerciseRepository;
import com.example.FitLog.workout.persistence.WorkoutRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WorkoutServiceTest {

    @Mock
    private WorkoutRepository workoutRepository;

    @Mock
    private WorkoutExerciseRepository workoutExerciseRepository;

    @Mock
    private ExerciseRepository exerciseRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private WorkoutService workoutService;

    private UUID userId;
    private UUID otherUserId;
    private UUID workoutId;
    private UUID exerciseId;
    private UUID workoutExerciseId;
    private UserEntity userEntity;
    private ExerciseEntity exerciseEntity;
    private WorkoutEntity workoutEntity;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        otherUserId = UUID.randomUUID();
        workoutId = UUID.randomUUID();
        exerciseId = UUID.randomUUID();
        workoutExerciseId = UUID.randomUUID();

        userEntity = UserEntity.builder()
                .id(userId)
                .name("John")
                .email("john@example.com")
                .build();

        exerciseEntity = ExerciseEntity.builder()
                .id(exerciseId)
                .name("Bench Press")
                .build();

        workoutEntity = WorkoutEntity.builder()
                .id(workoutId)
                .name("Chest Day")
                .description("Push day")
                .user(userEntity)
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

    // ---- createWorkout ----

    @Test
    void createWorkout_shouldReturnOk_whenValidInput() {
        mockSecurityContext(userId);
        when(userRepository.getReferenceById(userId)).thenReturn(userEntity);

        WorkoutDTO.PostOutput output = workoutService.createWorkout("Chest Day", "Push day");

        assertThat(output).isNotNull();
        assertThat(output.getName()).isEqualTo("Chest Day");
        assertThat(output.getDescription()).isEqualTo("Push day");
        verify(workoutRepository).save(any(WorkoutEntity.class));
    }

    @Test
    void createWorkout_shouldWork_whenDescriptionIsNull() {
        mockSecurityContext(userId);
        when(userRepository.getReferenceById(userId)).thenReturn(userEntity);

        WorkoutDTO.PostOutput output = workoutService.createWorkout("Chest Day", null);

        assertThat(output.getName()).isEqualTo("Chest Day");
        assertThat(output.getDescription()).isNull();
    }

    // ---- getWorkout ----

    @Test
    void getWorkout_shouldReturnWorkout_whenOwner() {
        mockSecurityContext(userId);
        when(workoutRepository.findById(workoutId)).thenReturn(Optional.of(workoutEntity));

        WorkoutDTO.GetOutput output = workoutService.getWorkout(workoutId);

        assertThat(output).isNotNull();
        assertThat(output.getName()).isEqualTo("Chest Day");
    }

    @Test
    void getWorkout_shouldThrowForbidden_whenNotOwner() {
        mockSecurityContext(otherUserId);
        when(workoutRepository.findById(workoutId)).thenReturn(Optional.of(workoutEntity));

        assertThatThrownBy(() -> workoutService.getWorkout(workoutId))
                .isInstanceOf(WorkoutException.class)
                .hasMessage("Access denied to this workout");
    }

    @Test
    void getWorkout_shouldThrowNotFound_whenDoesNotExist() {
        when(workoutRepository.findById(workoutId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> workoutService.getWorkout(workoutId))
                .isInstanceOf(WorkoutException.class)
                .hasMessage("Workout not found");
    }

    // ---- deleteWorkout ----

    @Test
    void deleteWorkout_shouldSucceed_whenOwner() {
        mockSecurityContext(userId);
        when(workoutRepository.findById(workoutId)).thenReturn(Optional.of(workoutEntity));

        workoutService.deleteWorkout(workoutId);

        verify(workoutRepository).delete(workoutEntity);
    }

    @Test
    void deleteWorkout_shouldThrowForbidden_whenNotOwner() {
        mockSecurityContext(otherUserId);
        when(workoutRepository.findById(workoutId)).thenReturn(Optional.of(workoutEntity));

        assertThatThrownBy(() -> workoutService.deleteWorkout(workoutId))
                .isInstanceOf(WorkoutException.class)
                .hasMessage("Access denied to this workout");
    }

    @Test
    void deleteWorkout_shouldThrowNotFound_whenDoesNotExist() {
        when(workoutRepository.findById(workoutId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> workoutService.deleteWorkout(workoutId))
                .isInstanceOf(WorkoutException.class)
                .hasMessage("Workout not found");
    }

    // ---- addExercise ----

    @Test
    void addExercise_shouldReturnOk_whenValidInput() {
        mockSecurityContext(userId);
        when(workoutRepository.findById(workoutId)).thenReturn(Optional.of(workoutEntity));
        when(exerciseRepository.findById(exerciseId)).thenReturn(Optional.of(exerciseEntity));

        List<WorkoutDTO.SetInput> sets = List.of(
                WorkoutDTO.SetInput.builder().repetition(10).kg(80).build(),
                WorkoutDTO.SetInput.builder().repetition(8).kg(85).build()
        );

        WorkoutDTO.AddExerciseOutput output = workoutService.addExercise(workoutId, exerciseId, sets);

        assertThat(output).isNotNull();
        assertThat(output.getExerciseName()).isEqualTo("Bench Press");
        assertThat(output.getSets()).hasSize(2);
        assertThat(output.getSets().get(0).getRepetition()).isEqualTo(10);
        assertThat(output.getSets().get(0).getKg()).isEqualTo(80);
        verify(workoutExerciseRepository).save(any(WorkoutExerciseEntity.class));
    }

    @Test
    void addExercise_shouldThrowForbidden_whenNotOwner() {
        mockSecurityContext(otherUserId);
        when(workoutRepository.findById(workoutId)).thenReturn(Optional.of(workoutEntity));

        assertThatThrownBy(() -> workoutService.addExercise(workoutId, exerciseId, List.of()))
                .isInstanceOf(WorkoutException.class)
                .hasMessage("Access denied to this workout");
    }

    @Test
    void addExercise_shouldThrowNotFound_whenWorkoutDoesNotExist() {
        when(workoutRepository.findById(workoutId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> workoutService.addExercise(workoutId, exerciseId, List.of()))
                .isInstanceOf(WorkoutException.class)
                .hasMessage("Workout not found");
    }

    @Test
    void addExercise_shouldThrowNotFound_whenExerciseDoesNotExist() {
        mockSecurityContext(userId);
        when(workoutRepository.findById(workoutId)).thenReturn(Optional.of(workoutEntity));
        when(exerciseRepository.findById(exerciseId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> workoutService.addExercise(workoutId, exerciseId, List.of()))
                .isInstanceOf(ExerciseException.class)
                .hasMessage("Exercise not found");
    }

    // ---- removeExercise ----

    @Test
    void removeExercise_shouldSucceed_whenWorkoutExerciseBelongsToWorkout() {
        WorkoutExerciseEntity workoutExercise = WorkoutExerciseEntity.builder()
                .id(workoutExerciseId)
                .workout(workoutEntity)
                .exercise(exerciseEntity)
                .sets(new ArrayList<>())
                .build();
        when(workoutExerciseRepository.findById(workoutExerciseId)).thenReturn(Optional.of(workoutExercise));

        workoutService.removeExercise(workoutId, workoutExerciseId);

        verify(workoutExerciseRepository).delete(workoutExercise);
    }

    @Test
    void removeExercise_shouldThrowForbidden_whenWorkoutExerciseDoesNotBelongToWorkout() {
        UUID otherWorkoutId = UUID.randomUUID();
        WorkoutEntity otherWorkout = WorkoutEntity.builder().id(otherWorkoutId).build();
        WorkoutExerciseEntity workoutExercise = WorkoutExerciseEntity.builder()
                .id(workoutExerciseId)
                .workout(otherWorkout)
                .build();
        when(workoutExerciseRepository.findById(workoutExerciseId)).thenReturn(Optional.of(workoutExercise));

        assertThatThrownBy(() -> workoutService.removeExercise(workoutId, workoutExerciseId))
                .isInstanceOf(WorkoutException.class)
                .hasMessage("Access denied to this workout");
    }

    @Test
    void removeExercise_shouldThrowNotFound_whenDoesNotExist() {
        when(workoutExerciseRepository.findById(workoutExerciseId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> workoutService.removeExercise(workoutId, workoutExerciseId))
                .isInstanceOf(WorkoutException.class)
                .hasMessage("Workout not found");
    }
}
