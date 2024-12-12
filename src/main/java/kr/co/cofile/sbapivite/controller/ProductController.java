package kr.co.cofile.sbapivite.controller;

import kr.co.cofile.sbapivite.dto.*;
import kr.co.cofile.sbapivite.enums.SortOrder;
import kr.co.cofile.sbapivite.service.ProductService;
import kr.co.cofile.sbapivite.util.CustomFileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/products")
public class ProductController {

    private final CustomFileUtil fileUtil;
    private final ProductService productService;

    @PostMapping
    public Map<String, Long> addProduct(@ModelAttribute ProductRequest productRequest,
                                        @RequestParam(value = "files", required = false) List<MultipartFile> files) {

        log.info("addProduct: " + productRequest);

//        List<MultipartFile> files = productRequest.getFiles();

        List<String> uploadFileNames = fileUtil.saveFiles(files);

        //productRequest.setUploadFileNames(uploadFileNames);

        //log.info(uploadFileNames);

        Long pno = productService.addProduct(productRequest);

        return Map.of("result", pno);
    }

    @GetMapping("/view/{fileName}")
    public ResponseEntity<Resource> viewFileGET(@PathVariable String fileName) {
        return fileUtil.getFile(fileName);
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

}
