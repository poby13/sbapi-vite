package kr.co.cofile.sbapivite.entity;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@ToString(exclude = "imageList")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Product {

    private Long pno;
    private String pname;
    private int price;
    private String pdesc;
    private boolean delFlag;

    private List<ProductImage> images = new ArrayList<>();

    public void changePrice(int price) {
        this.price = price;
    }

    public void changeDesc(String pdesc) {
        this.pdesc = pdesc;
    }

    public void addImage(ProductImage image) {
        image.setOrd(this.images.size());
        images.add(image);
    }

    public void addImageString(String fileName) {
        ProductImage productImage = ProductImage.builder()
                .fileName(fileName)
                .build();

        addImage(productImage);
    }

    public void clearList() {
        this.images.clear();
    }
}
