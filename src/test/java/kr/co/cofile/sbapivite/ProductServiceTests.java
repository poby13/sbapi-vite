package kr.co.cofile.sbapivite;

import kr.co.cofile.sbapivite.dto.*;
import kr.co.cofile.sbapivite.enums.SortOrder;
import kr.co.cofile.sbapivite.service.ProductService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@Log4j2
public class ProductServiceTests {

    @Autowired
    ProductService productService;

    @Test
    public void testAddProduct() {

        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "file","test.txt","text/plain","테스트파일".getBytes()
        );

        List<MultipartFile> multipartFiles = new ArrayList<>();
        multipartFiles.add(mockMultipartFile);
        multipartFiles.add(mockMultipartFile);
        multipartFiles.add(mockMultipartFile);

        ProductRequest productRequest = ProductRequest.builder()
                .pname(UUID.randomUUID().toString() + "_" + "상품1")
                .price(100)
                .pdesc("설명")
                .files(multipartFiles)
                .build();

        productService.addProduct(productRequest);
    }

    @Test
    public void testListProduct() {
        PageRequest pageRequest = PageRequest.builder()
                .page(1)
                .size(10)
                .sortOrder(SortOrder.DESC)
                .build();
        PageResponse<ProductResponse> pageResponse = productService.listProduct(pageRequest);

        log.info(pageResponse.toString());
    }

}
