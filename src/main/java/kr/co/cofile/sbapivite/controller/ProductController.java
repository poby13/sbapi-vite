package kr.co.cofile.sbapivite.controller;

import kr.co.cofile.sbapivite.dto.*;
import kr.co.cofile.sbapivite.enums.SortOrder;
import kr.co.cofile.sbapivite.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

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
        productRequest.setPno(pno);
        return productService.modifyProduct(productRequest);
    }

    @DeleteMapping("/{pno}")
    public void removeProduct(@PathVariable("pno") Long pno) {
        productService.removeProduct(pno);
    }
}