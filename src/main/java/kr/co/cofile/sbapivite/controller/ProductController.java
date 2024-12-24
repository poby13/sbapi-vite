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

    @GetMapping("/{id}")
    public ProductResponse findProductById(@PathVariable("id") Long id) {
        return productService.findProductById(id);
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

    @PutMapping("/{id}")
    public ProductAddResponse modifyProduct(@PathVariable("id") Long id,
                                            @ModelAttribute ProductRequest productRequest) {
        productRequest.setId(id);
        return productService.modifyProduct(productRequest);
    }

    @DeleteMapping("/{id}")
    public void removeProduct(@PathVariable("id") Long id) {
        productService.removeProduct(id);
    }
}