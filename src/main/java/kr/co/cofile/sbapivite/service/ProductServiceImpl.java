package kr.co.cofile.sbapivite.service;

import kr.co.cofile.sbapivite.dto.PageRequest;
import kr.co.cofile.sbapivite.dto.PageResponse;
import kr.co.cofile.sbapivite.dto.ProductRequest;
import kr.co.cofile.sbapivite.dto.ProductResponse;
import kr.co.cofile.sbapivite.entity.Product;
import kr.co.cofile.sbapivite.entity.ProductImage;
import kr.co.cofile.sbapivite.enums.SortOrder;
import kr.co.cofile.sbapivite.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final ModelMapper modelMapper;
    private final ProductMapper productMapper;

    @Override
    @Transactional
    public Long addProduct(ProductRequest productRequest) {

        Product product = modelMapper.map(productRequest, Product.class);

        productMapper.insertProduct(product);

        List<MultipartFile> files = productRequest.getFiles();

        long savedPno = product.getPno();

        int seq = 0;

        for (MultipartFile file : files) {
            ProductImage productImage = ProductImage.builder()
                    .productPno(savedPno)
                    .fileName(UUID.randomUUID().toString() + "_" + file.getOriginalFilename())
                    .filePath("images")
                    .fileType(file.getContentType())
                    .sequence(++seq)
                    .build();

            productMapper.insertProductImage(productImage);
        }

        return savedPno;
    }

    @Override
    public PageResponse<ProductResponse> listProduct(PageRequest pageRequest) {

        // Enum을 String으로 처리
        List<Product> products = productMapper.selectAllProduct(pageRequest.getSortOrder().name(), pageRequest.getOffset(), pageRequest.getSize());
        List<ProductResponse> productResponses = products.stream()
                .map(product -> modelMapper.map(product, ProductResponse.class))
                .toList();

        int totalElements = productMapper.countTotalProduct();
        int currentPage = pageRequest.getPage();
        int size = pageRequest.getSize();
        SortOrder sortOrder = pageRequest.getSortOrder();

        return new PageResponse<>(productResponses, totalElements, currentPage, size, sortOrder);
    }
}
