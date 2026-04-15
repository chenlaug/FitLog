package com.example.FitLog.exercise.service;

import com.example.FitLog.exercise.DTO.ExerciseDTO;
import com.example.FitLog.exercise.model.ExerciseEntity;
import com.example.FitLog.exercise.model.exception.ExerciseException;
import com.example.FitLog.exercise.persistence.ExerciseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExerciseServiceTest {

    @Mock
    private ExerciseRepository exerciseRepository;

    @InjectMocks
    private ExerciseService exerciseService;

    private UUID exerciseId;
    private ExerciseEntity exerciseEntity;

    @BeforeEach
    void setUp() {
        exerciseId = UUID.randomUUID();
        exerciseEntity = ExerciseEntity.builder()
                .id(exerciseId)
                .name("Bench Press")
                .build();
    }

    // ---- createExercise ----

    @Test
    void createExercise_shouldReturnOk_whenValidName() {
        ResponseEntity<ExerciseDTO.PostOutput> response = exerciseService.createExercise("Bench Press");

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Bench Press");
        verify(exerciseRepository).save(any(ExerciseEntity.class));
    }

    @Test
    void createExercise_shouldThrowBadRequest_whenNameIsNull() {
        assertThatThrownBy(() -> exerciseService.createExercise(null))
                .isInstanceOf(ExerciseException.class)
                .hasMessageContaining("Name cannot be blank");
    }

    @Test
    void createExercise_shouldThrowBadRequest_whenNameIsBlank() {
        assertThatThrownBy(() -> exerciseService.createExercise("   "))
                .isInstanceOf(ExerciseException.class)
                .hasMessageContaining("Name cannot be blank");
    }

    // ---- deleteExercise ----

    @Test
    void deleteExercise_shouldReturnOk_whenExerciseExists() {
        when(exerciseRepository.existsById(exerciseId)).thenReturn(true);

        ResponseEntity<ExerciseDTO.DeleteOutput> response = exerciseService.deleteExercise(exerciseId);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getMessage()).isEqualTo("Exercise deleted successfully.");
        verify(exerciseRepository).deleteById(exerciseId);
    }

    @Test
    void deleteExercise_shouldThrowNotFound_whenExerciseDoesNotExist() {
        when(exerciseRepository.existsById(exerciseId)).thenReturn(false);

        assertThatThrownBy(() -> exerciseService.deleteExercise(exerciseId))
                .isInstanceOf(ExerciseException.class)
                .hasMessage("Exercise not found");
    }

    // ---- getExerciseById ----

    @Test
    void getExerciseById_shouldReturnExercise_whenExists() {
        when(exerciseRepository.findById(exerciseId)).thenReturn(Optional.of(exerciseEntity));

        ResponseEntity<ExerciseDTO.GetOutput> response = exerciseService.getExerciseById(exerciseId);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getName()).isEqualTo("Bench Press");
        assertThat(response.getBody().getId()).isEqualTo(exerciseId);
    }

    @Test
    void getExerciseById_shouldThrowNotFound_whenDoesNotExist() {
        when(exerciseRepository.findById(exerciseId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> exerciseService.getExerciseById(exerciseId))
                .isInstanceOf(ExerciseException.class)
                .hasMessage("Exercise not found");
    }

    // ---- getAllExercises ----

    @Test
    void getAllExercises_shouldReturnPage_whenCalled() {
        Page<ExerciseEntity> page = new PageImpl<>(List.of(exerciseEntity));
        when(exerciseRepository.findAll(PageRequest.of(0, 10))).thenReturn(page);

        ResponseEntity<Page<ExerciseDTO.GetOutput>> response = exerciseService.getAllExercises(0, 10);

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().getContent()).hasSize(1);
        assertThat(response.getBody().getContent().get(0).getName()).isEqualTo("Bench Press");
    }

    // ---- updateExercise ----

    @Test
    void updateExercise_shouldUpdateName_whenValidName() {
        when(exerciseRepository.findById(exerciseId)).thenReturn(Optional.of(exerciseEntity));

        ResponseEntity<ExerciseDTO.PatchOutput> response = exerciseService.updateExercise(exerciseId, "Squat");

        assertThat(response.getStatusCode().value()).isEqualTo(200);
        assertThat(exerciseEntity.getName()).isEqualTo("Squat");
        verify(exerciseRepository).save(exerciseEntity);
    }

    @Test
    void updateExercise_shouldThrowNotFound_whenExerciseDoesNotExist() {
        when(exerciseRepository.findById(exerciseId)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> exerciseService.updateExercise(exerciseId, "Squat"))
                .isInstanceOf(ExerciseException.class)
                .hasMessage("Exercise not found");
    }

    @Test
    void updateExercise_shouldThrowBadRequest_whenNameIsBlank() {
        when(exerciseRepository.findById(exerciseId)).thenReturn(Optional.of(exerciseEntity));

        assertThatThrownBy(() -> exerciseService.updateExercise(exerciseId, "   "))
                .isInstanceOf(ExerciseException.class)
                .hasMessageContaining("Name cannot be blank");
    }

    @Test
    void updateExercise_shouldNotUpdateName_whenNameIsNull() {
        when(exerciseRepository.findById(exerciseId)).thenReturn(Optional.of(exerciseEntity));

        exerciseService.updateExercise(exerciseId, null);

        assertThat(exerciseEntity.getName()).isEqualTo("Bench Press");
        verify(exerciseRepository).save(exerciseEntity);
    }
}
