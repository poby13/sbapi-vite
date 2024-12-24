package kr.co.cofile.sbapivite.service;

import kr.co.cofile.sbapivite.dto.*;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface ProductService {

    ProductAddResponse addProduct(ProductRequest productRequest);

    ProductResponse findProductById(Long pno);

    ProductAddResponse modifyProduct(ProductRequest productRequest);

    void removeProduct(Long pno);

    PageResponse<ProductResponse> listProduct(PageRequest pageRequest);

}
