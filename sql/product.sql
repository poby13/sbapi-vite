CREATE TABLE products
(
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    del_flag BOOLEAN DEFAULT FALSE NOT NULL,
    pdesc    VARCHAR(255),
    pname    VARCHAR(255)          NOT NULL,
    price    INTEGER               NOT NULL
);

CREATE TABLE product_images
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,   -- 이미지의 고유 ID
    product_id     BIGINT       NOT NULL,               -- 참조할 제품 ID
    file_name      VARCHAR(255) NOT NULL,               -- 파일명
    file_path      VARCHAR(500),                        -- 파일 경로나 URL
    file_type      VARCHAR(50),                         -- 파일 유형 (예: JPG, PNG 등)
    thumbnail_path VARCHAR(500),                        -- 썸네일 파일 경로나 URL
    uploaded_at    TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 업로드 날짜
    sequence       INTEGER      NOT NULL,               -- 이미지 순서
    FOREIGN KEY (PRODUCT_ID) REFERENCES products (id) ON DELETE CASCADE
);