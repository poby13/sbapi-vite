package kr.co.cofile.sbapivite.service;

import kr.co.cofile.sbapivite.dto.*;
import kr.co.cofile.sbapivite.entity.Product;
import kr.co.cofile.sbapivite.entity.ProductImage;
import kr.co.cofile.sbapivite.enums.SortOrder;
import kr.co.cofile.sbapivite.mapper.ProductMapper;
import kr.co.cofile.sbapivite.util.CustomFileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class ProductServiceImpl implements ProductService {

    private final CustomFileUtil customFileUtil;
    private final ModelMapper modelMapper;
    private final ProductMapper productMapper;

    @Override
    @Transactional
    public ProductAddResponse addProduct(ProductRequest productRequest) {

        // 상품정보 등록
        Product product = modelMapper.map(productRequest, Product.class);
        productMapper.insertProduct(product);

        // 새로 등록된 상품의 아이디
        Long pno = product.getPno();

        // 새 파일 저장 및 썸네일 생성
        // 모든 썸네일의 확장자는 .jpg이다
        List<ProductImage> uploadedImages = saveNewProductImages(product, productRequest);

        // 응답 생성
        return createProductAddResponse(pno, uploadedImages);
    }

    @Override
    public ProductResponse findProductById(Long pno) {
        Optional<Product> result = productMapper.selectProductById(pno);
        Product product = result.orElseThrow();

        return modelMapper.map(product, ProductResponse.class);
    }

    @Override
    @Transactional
    public ProductAddResponse modifyProduct(ProductRequest productRequest) {

        // 상품 조회
        Long pno = productRequest.getPno();
        Optional<Product> savedProduct = productMapper.selectProductById(pno);
        Product product = savedProduct.orElseThrow(() -> new NoSuchElementException("상품을 찾을 수 없습니다." + pno));

        // 기존 상품 정보 업데이트
        updateProduct(product, productRequest);

        // 첨부파일 삭제
        List<ProductImage> productImages = productMapper.selectImagesByProductId(pno);
        List<String> files = productImages.stream().map(ProductImage::getFilePath).toList();
        customFileUtil.deleteFiles(files);
        // 기존 이미지 삭제
        productMapper.deleteImagesByProductId(pno);


        // 새 파일 저장 및 썸네일 생성
        // 모든 썸네일의 확장자는 .jpg이다
        List<ProductImage> uploadedImages = saveNewProductImages(product, productRequest);

        // 응답 생성
        return createProductAddResponse(pno, uploadedImages);
    }

    // 상품 정보 업데이트
    private void updateProduct(Product product, ProductRequest productRequest) {
        product.setPname(productRequest.getPname());
        product.setPrice(productRequest.getPrice());
        product.setPdesc(productRequest.getPdesc());

        // 기존 이미지 삭제
        product.clearList();

        // 상품정보 수정
        productMapper.updateProduct(product);

    }

    // 새 이미지 저장
    private List<ProductImage> saveNewProductImages(Product product, ProductRequest productRequest) {
        List<MultipartFile> multipartFiles = productRequest.getFiles();
        List<ProductImage> uploadedImages = customFileUtil.saveFiles(multipartFiles);

        for (ProductImage productImage : uploadedImages) {
            productImage.setProductPno(product.getPno());
            productMapper.insertProductImage(productImage);
        }

        return uploadedImages;
    }

    // 응답 객체 생성
    private ProductAddResponse createProductAddResponse(Long pno, List<ProductImage> uploadedImages) {
        // 첨부된 파일명 추출
        List<String> savedFileNames = uploadedImages.stream()
                .map(image -> image.getFileName().substring(image.getFileName().lastIndexOf("_") + 1))
                .toList();

        return ProductAddResponse.builder()
                .pno(pno)
                .uploadFileNames(savedFileNames)
                .build();
    }

    // TODO removeProduct

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
