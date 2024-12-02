package kr.co.cofile.sbapivite.dto;

import kr.co.cofile.sbapivite.domain.SortOrder;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Builder
public class PageRequest {

    @Builder.Default
    private int page = 1;  // 기본 페이지는 1

    @Builder.Default
    private int size = 10; // 기본 페이지 크기는 10

    @Builder.Default
    private SortOrder sortOrder = SortOrder.DESC;

    public int getOffset() {
        return (page - 1) * size;
    }
}
