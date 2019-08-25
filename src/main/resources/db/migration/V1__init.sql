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

CREATE TABLE events (
    id serial,
    type varchar(50) NOT NULL,
    start timestamp,
    ended timestamp,
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

CREATE TABLE users_events (
    user_id     int NOT NULL,
    event_id    int NOT NULL,
    PRIMARY KEY (user_id, event_id),

    CONSTRAINT FK_USER_ID_02 FOREIGN KEY (user_id)
    REFERENCES users (id)
    ON DELETE NO ACTION ON UPDATE NO ACTION,

    CONSTRAINT FK_HABIT_ID FOREIGN KEY (event_id)
    REFERENCES events (id)
    ON DELETE NO ACTION ON UPDATE NO ACTION
);

INSERT INTO events (type, start, ended)
VALUES
('Run', '2019-08-25', '2019-08-26');

INSERT INTO roles (name)
VALUES
('ROLE_USER'), ('ROLE_ADMIN');

INSERT INTO users (username, password, name, email)
VALUES
('admin', '$2a$04$NYqjpQYkO9H3AEJt0eT3ROHPgw5MDuqWuE7WHXlQwi5Sg0e2j3WNu', 'Bender', 'bender@mail.ru');


INSERT INTO users_roles (user_id, role_id)
VALUES
(1, 1), (1, 2);
