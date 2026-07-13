DROP TABLE IF EXISTS authorities;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS contact;

CREATE TABLE contact (
    id BIGSERIAL PRIMARY KEY,
    last_name VARCHAR(40) NOT NULL,
    first_name VARCHAR(40) NOT NULL,
    mi CHAR(1),
    email VARCHAR(80),
    date_created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    date_modified TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT contact_idx1 UNIQUE (last_name, first_name, mi)
);

CREATE TABLE users (
    username VARCHAR(50) PRIMARY KEY,
    password VARCHAR(50) NOT NULL,
    enabled BOOLEAN NOT NULL
);

CREATE TABLE authorities (
    username VARCHAR(50) NOT NULL REFERENCES users (username),
    authority VARCHAR(50) NOT NULL,
    CONSTRAINT authorities_idx_1 UNIQUE (username, authority)
);

INSERT INTO users (username, password, enabled) VALUES ('juan', 'password', true);
INSERT INTO authorities (username, authority) VALUES ('juan', 'user');
