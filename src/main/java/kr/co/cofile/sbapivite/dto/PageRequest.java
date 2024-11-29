package kr.co.cofile.sbapivite.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageRequest {
    private int page = 1;  // 기본 페이지는 1
    private int size = 10; // 기본 페이지 크기는 10

    public int getOffset() {
        return (page - 1) * size;
    }
}
