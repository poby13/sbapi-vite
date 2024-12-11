package kr.co.cofile.sbapivite.entity;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(exclude = "images")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private Long pno;
    private String pname;
    private int price;
    private String pdesc;
    private boolean delFlag;

    @Builder.Default
    private List<ProductImage> images = new ArrayList<>();

    public void changeDel(boolean delFlag) {
        this.delFlag = delFlag;
    }

    public void addImage(ProductImage image) {
        image.setSequence(this.images.size()); // 현재 파일의 갯수를 추가 순서로 사용
        images.add(image);
    }

    public void clearList() {
        this.images.clear();
    }
}
