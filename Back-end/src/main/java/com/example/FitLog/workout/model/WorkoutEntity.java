package com.example.FitLog.workout.model;

import com.example.FitLog.exercise.model.ExerciseEntity;
import com.example.FitLog.user.model.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "Workouts")
public class WorkoutEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID uuid;
    String name;
    String description;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToMany
    @JoinTable(
            name = "workout_exercises",
            joinColumns = @JoinColumn(name = "workout_id"),
            inverseJoinColumns = @JoinColumn(name = "exercise_id")
    )
    @Builder.Default
    private List<ExerciseEntity> exercises = new ArrayList<>();
}
