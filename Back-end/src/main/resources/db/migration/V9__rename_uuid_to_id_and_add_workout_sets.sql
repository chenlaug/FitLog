-- Rename uuid → id in all tables
ALTER TABLE users RENAME COLUMN uuid TO id;
ALTER TABLE exercises RENAME COLUMN uuid TO id;
ALTER TABLE workouts RENAME COLUMN uuid TO id;

-- Drop old ManyToMany join table
DROP TABLE workout_exercises;

-- Recreate workout_exercises as a real entity table
CREATE TABLE workout_exercises
(
    id          UUID PRIMARY KEY,
    workout_id  UUID REFERENCES workouts (id) ON DELETE CASCADE,
    exercise_id UUID REFERENCES exercises (id) ON DELETE SET NULL
);

-- Create sets table
CREATE TABLE sets
(
    id                  UUID PRIMARY KEY,
    repetition          INTEGER NOT NULL,
    kg                  INTEGER NOT NULL,
    workout_exercise_id UUID REFERENCES workout_exercises (id) ON DELETE CASCADE
);
