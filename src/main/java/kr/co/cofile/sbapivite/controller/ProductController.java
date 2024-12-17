package kr.co.cofile.sbapivite.controller;

import kr.co.cofile.sbapivite.dto.*;
import kr.co.cofile.sbapivite.enums.SortOrder;
import kr.co.cofile.sbapivite.service.MinioService;
import kr.co.cofile.sbapivite.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/products")
public class ProductController {

    @Value("${minio.url}")
    private String minioUrl;

    @Value("${minio.bucketName}")
    private String minioBucketName;

    private final ProductService productService;

    private final MinioService minioService;

    @PostMapping
    public ProductAddResponse addProduct(@ModelAttribute ProductRequest productRequest) {
        return productService.addProduct(productRequest);
    }

    @GetMapping("/{pno}")
    public ProductResponse findProductById(@PathVariable("pno") Long pno) {
        return productService.findProductById(pno);
    }

    @GetMapping("/list")
    public PageResponse<ProductResponse> listProduct(@RequestParam(name = "page", required = false) Integer page,
                                               @RequestParam(name = "size", required = false) Integer size,
                                               @RequestParam(name = "sort_order", required = false) SortOrder sortOrder) {

        PageRequest pageRequest = PageRequest.builder()
                .page(page != null ? page : 1)
                .size(size != null ? size : 10)
                .sortOrder(sortOrder != null ? sortOrder : SortOrder.DESC)
                .build();

        return productService.listProduct(pageRequest);
    }

    @PutMapping("/{pno}")
    public ProductAddResponse modifyProduct(@PathVariable("pno") Long pno,
                                                             @ModelAttribute ProductRequest productRequest) {
        return productService.modifyProduct(productRequest);
    }

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            String fileName = minioService.uploadFile(file);
            String fileUrl = minioUrl + "/" + minioBucketName + "/" + fileName;
            return ResponseEntity.ok(fileUrl);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}