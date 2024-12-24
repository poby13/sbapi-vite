package kr.co.cofile.sbapivite.dto;

import kr.co.cofile.sbapivite.entity.ProductImage;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductResponse {

    private Long id;
    private String pname;
    private int price;
    private String pdesc;
    private boolean delFlag;

    @Builder.Default
    private List<ProductImage> productImages = new ArrayList<>();

}
