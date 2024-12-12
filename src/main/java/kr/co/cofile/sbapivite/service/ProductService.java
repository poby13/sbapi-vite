package kr.co.cofile.sbapivite.service;

import kr.co.cofile.sbapivite.dto.*;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ProductService {

    Long addProduct(ProductRequest productRequest);
    ProductResponse findProductById(Long pno);
    PageResponse<ProductResponse> listProduct(PageRequest pageRequest);

}
