create table tbl_product (
	pno bigint not null auto_increment,
	del_flag bit not null,
	pdesc varchar(255),
	pname varchar(255),
	price integer not null,
	primary key (pno)
);

CREATE TABLE product_image_list (
    image_id BIGINT NOT NULL AUTO_INCREMENT, -- 이미지의 고유 ID
    product_pno BIGINT NOT NULL,             -- 참조할 제품 ID
    file_name VARCHAR(255) NOT NULL,         -- 파일명
    file_path VARCHAR(500),                  -- 파일 경로나 URL
    file_type VARCHAR(50),                   -- 파일 유형 (예: jpg, png 등)
    thumbnail_path VARCHAR(500),             -- 썸네일 파일 경로나 URL
    upload_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 업로드 날짜
    sequence INTEGER NOT NULL,               -- 이미지 순서
    PRIMARY KEY (image_id),
    FOREIGN KEY (product_pno) REFERENCES tbl_product(pno) ON DELETE CASCADE
);