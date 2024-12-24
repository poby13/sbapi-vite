# use malldb;
CREATE DATABASE mall_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE mall_db;

CREATE TABLE todos
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    title    VARCHAR(255)          NOT NULL,
    writer   VARCHAR(255)          NOT NULL,
    complete BOOLEAN DEFAULT FALSE NOT NULL,
    due_date DATE
);