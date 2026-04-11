ALTER TABLE app_user RENAME TO "Users";

CREATE TABLE exercises
(
    uuid   UUID PRIMARY KEY,
    name   VARCHAR(255),
    weight INTEGER,
    kg     INTEGER
);

CREATE TABLE workouts
(
    uuid        UUID PRIMARY KEY,
    name        VARCHAR(255),
    description VARCHAR(255),
    user_id     UUID REFERENCES "Users" (uuid) ON DELETE CASCADE
);

CREATE TABLE workout_exercises
(
    workout_id  UUID REFERENCES workouts (uuid) ON DELETE CASCADE,
    exercise_id UUID REFERENCES exercises (uuid) ON DELETE CASCADE,
    PRIMARY KEY (workout_id, exercise_id)
);
