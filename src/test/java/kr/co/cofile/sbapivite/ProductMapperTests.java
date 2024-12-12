package kr.co.cofile.sbapivite;

import kr.co.cofile.sbapivite.entity.Product;
import kr.co.cofile.sbapivite.entity.ProductImage;
import kr.co.cofile.sbapivite.mapper.ProductMapper;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Log4j2
public class ProductMapperTests {

    @Autowired
    ProductMapper productMapper;

    // Product
    private Product createTestProduct(int index) {
        return Product.builder()
                .delFlag(false)
                .pdesc("설명" + index)
                .pname("이름" + index)
                .price(100 * index)
                .build();
    }

    // ProductImage
    private ProductImage createTestProductImage(long productPno, int sequence) {
        return ProductImage.builder()
                .productPno(productPno)
                .fileName(UUID.randomUUID().toString() + "_이미지" + sequence + ".jpg")
                .filePath("/images/")
                .fileType("jpg")
                .sequence(sequence)
                .build();
    }


    @Test
    @DisplayName("Product 삽입 테스트")
    public void testInsertProduct() {
        for (int i = 0; i < 10; i++) {
            Product product = createTestProduct(i);

            productMapper.insertProduct(product);

            assertNotNull(product.getPno(), "Product Id는 삽입 후에 null일 수 없습니다.");
            log.info("삽입 Product: {}", product);
        }

    }

    @Test
    @DisplayName("Product Id로 조회 테스트")
    public void testSelectProductById() {
        long pno = 2L;

        Product product = productMapper.selectProductById(pno).orElseThrow(() -> new IllegalArgumentException("Product를 찾을 수 없습니다: " + pno));

        assertEquals(2L, product.getPno(), "Product Id가 일치하지 않음.");
        log.info("조회 Product: {}",product);
    }

    @Test
    @DisplayName("Product 수정 테스트")
    public void testUpdateProduct() {
        long pno = 2L;
        Product product = productMapper.selectProductById(pno).orElseThrow();

        String savedPname = product.getPname();

        product.setPname(savedPname + "(수정)");

//        product.clearList();
//
//        for (int i=0; i < 3; i++) {
//            ProductImage productImage = createTestProductImage(pno, i);
//            product.addImage(productImage);
//        }

        productMapper.updateProduct(product);
    }

    @Test
    @DisplayName("Product 삭제 테스트")
    public void testDeleteProductById() {
        long pno = 1L;

        productMapper.deleteProductById(pno);

        Optional<Product> deletedProduct = productMapper.selectProductById(pno);
        assertTrue(deletedProduct.isEmpty(), "Product는 삭제되어야 합니다.");
    }

    @Test
    @DisplayName("Product 삭제 상태로 변경 테스트")
    public void testUpdateToDeleteProductById() {
        long productPno = 5L;
        boolean delFlag = true;

        productMapper.updateToDeleteProduct(productPno, delFlag);
    }

    @Test
    @DisplayName("이미지 등록 테스트")
    public void testInsertProductImage() {
        long productPno = 5L;
        for (int i = 0; i < 10; i++) {
            ProductImage productImage = createTestProductImage(productPno, i);

            productMapper.insertProductImage(productImage);

            assertNotNull(productImage.getImageId(), "이미지 Id는 등록후 null일 수 없습니다.");
            log.info("등록 이미지: {}", productImage);
        }
    }

    @Test
    @DisplayName("Product의 모든 이미지 조회 테스트")
    public void testSelectImagesByProductId() {
        long productId = 5L;

        List<ProductImage> productImages = productMapper.selectImagesByProductId(productId);

        assertNotNull(productImages, "이미지는 null일 수 없습니다.");
        assertFalse(productImages.isEmpty(), "이미지는 비어 있을 수 없습니다.");
        log.info("이미지 목록: {}", productImages);
    }

    @Test
    @DisplayName("Product의 모든 이미지 삭제 테스트")
    public void testDeleteImagesByProductId() {
        long productId = 5L;

        productMapper.deleteImagesByProductId(productId);

        List<ProductImage> productImages = productMapper.selectImagesByProductId(productId);
        assertTrue(productImages.isEmpty(), "모든 Product 이미지를 삭제되어야 합니다.");
    }

    @Test
    @DisplayName("Product와 관련 이미지 조회 테스트")
    public void testSelectProductWithImageById() {
        long productId = 5L;

        Product product = productMapper.selectProductWithImagesById(productId).orElseThrow(() ->
                new IllegalArgumentException("Product를 찾을 수 없음: " + productId));

        assertNotNull(product.getProductImages(), "Product 이미지는 null일 수 없습니다.");
        log.info("이미지를 포함한 Product: {}", product);
    }
}
