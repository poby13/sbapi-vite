package kr.co.cofile.sbapivite.entity;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(exclude = "productImages")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private Long id;
    private String pname;
    private int price;
    private String pdesc;
    private boolean delFlag;

    @Builder.Default
    private List<ProductImage> productImages = new ArrayList<>();

    public void changeDel(boolean delFlag) {
        this.delFlag = delFlag;
    }

    public void addImage(ProductImage image) {
        image.setSequence(this.productImages.size()); // 현재 파일의 갯수를 추가 순서로 사용
        this.productImages.add(image);
    }

    public void clearList() {
        this.productImages.clear();
    }
}
