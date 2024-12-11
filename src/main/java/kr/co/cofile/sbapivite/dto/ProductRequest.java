package kr.co.cofile.sbapivite.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {

    private String pname;
    private int price;
    private String pdesc;

    @Builder.Default
    private List<MultipartFile> files = new ArrayList<>();

}
