CREATE TABLE users (
    id serial,
    username varchar(50) NOT NULL,
    password varchar(100) NOT NULL,
    name     varchar(100) NOT NULL,
    email    varchar,
    PRIMARY KEY (id)
);

CREATE TABLE roles (
    id      serial,
    name    varchar(50) DEFAULT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE habits (
    id serial,
    name varchar(100) NOT NULL,
    mark timestamp,
    PRIMARY KEY (id)
);

CREATE TABLE events (
    id serial,
    title varchar(50) NOT NULL,
    start varchar(50) NOT NULL,
    ended varchar(50) NOT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE users_roles (
    user_id     int NOT NULL,
    role_id     int NOT NULL,
    PRIMARY KEY (user_id, role_id),

    CONSTRAINT FK_USER_ID_01 FOREIGN KEY (user_id)
    REFERENCES users (id)
    ON DELETE NO ACTION ON UPDATE NO ACTION,

    CONSTRAINT FK_ROLE_ID FOREIGN KEY (role_id)
    REFERENCES roles (id)
    ON DELETE NO ACTION ON UPDATE NO ACTION
);

CREATE TABLE users_habits (
    user_id     int NOT NULL,
    habit_id    int NOT NULL,
    PRIMARY KEY (user_id, habit_id),

    CONSTRAINT FK_USER_ID_02 FOREIGN KEY (user_id)
    REFERENCES users (id)
    ON DELETE NO ACTION ON UPDATE NO ACTION,

    CONSTRAINT FK_HABIT_ID FOREIGN KEY (habit_id)
    REFERENCES habits (id)
    ON DELETE NO ACTION ON UPDATE NO ACTION
);

INSERT INTO roles (name)
VALUES
('ROLE_USER'), ('ROLE_ADMIN');

INSERT INTO users (username, password, name, email)
VALUES
('admin', '$2a$04$NYqjpQYkO9H3AEJt0eT3ROHPgw5MDuqWuE7WHXlQwi5Sg0e2j3WNu', 'Bender', 'bender@mail.ru');

INSERT INTO habits (name, mark)
VALUES
('user check', TIMESTAMP '2019-08-01 21:10:38'),
('user check', TIMESTAMP '2019-07-06 19:00:00'),
('user check', TIMESTAMP '2019-10-02 19:00:00'),
('user check', TIMESTAMP '2019-07-01 19:00:00'),
('user check', TIMESTAMP '2019-08-02 19:00:00');

INSERT INTO users_roles (user_id, role_id)
VALUES
(1, 1), (1, 2);
