package kr.co.cofile.sbapivite.mapper;

import kr.co.cofile.sbapivite.entity.Product;
import kr.co.cofile.sbapivite.entity.ProductImage;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ProductMapper {

    void insertProduct(Product product);
    Optional<Product> selectProductById(Long pno);
    void updateProduct(Product product);
    void deleteProductById(Long pno);

    void insertProductImage(ProductImage productImage);
    List<ProductImage> selectImagesByProductId(Long pno);
    void deleteImagesByProductId(Long pno);

    Optional<Product> selectProductWithImageById(Long pno);
}
