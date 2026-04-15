package com.example.FitLog.workout.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "sets")
public class SetEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    int repetition;
    int kg;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workout_exercise_id")
    WorkoutExerciseEntity workoutExercise;
}
