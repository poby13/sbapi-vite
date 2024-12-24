CREATE TABLE member_roles
(
    member_id BIGINT NOT NULL,
    role_id   BIGINT NOT NULL,
    PRIMARY KEY (member_id, role_id),
    CONSTRAINT FK_MEMBER_ROLES_MEMBERS FOREIGN KEY (member_id) REFERENCES members (id) ON DELETE CASCADE,
    CONSTRAINT FK_MEMBER_ROLES_ROLES FOREIGN KEY (role_id) REFERENCES roles (id) ON DELETE CASCADE
);

-- 복합키의 기본 인덱스는 WHERE member_id = ? AND role_id = ?나 복합키의 첫번째 컬럼이 조건으롤 사용될 때(WHERE member_id = ?) 효과적
-- role_id만 단독으로 검색 조건이나 조인이 자수 수행되면 필요
CREATE INDEX IDX_ROLE_ID ON member_roles (role_id);

CREATE TABLE members
(
    id         BIGINT AUTO_INCREMENT PRIMARY KEY,
    email      VARCHAR(255)                        NOT NULL,
    pw         VARCHAR(255)                        NOT NULL,
    nickname   VARCHAR(100)                        NULL,
    social     BOOLEAN   DEFAULT FALSE             NULL,
    enabled    BOOLEAN   DEFAULT TRUE              NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT UQ_EMAIL UNIQUE (email)
);

CREATE TABLE roles
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    role_name VARCHAR(50) NOT NULL,
    CONSTRAINT UQ_ROLE_NAME UNIQUE (role_name)
);