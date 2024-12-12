package kr.co.cofile.sbapivite.mapper;

import kr.co.cofile.sbapivite.entity.Product;
import kr.co.cofile.sbapivite.entity.ProductImage;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ProductMapper {

    @Insert("INSERT INTO tbl_product (del_flag, pdesc, pname, price) " +
            "VALUES (#{delFlag}, #{pdesc}, #{pname}, #{price})")
    @Options(useGeneratedKeys = true, keyProperty = "pno")
    void insertProduct(Product product);

    @Select("SELECT * FROM tbl_product WHERE pno = #{pno}")
    Optional<Product> selectProductById(Long pno);

    @Update("UPDATE tbl_product " +
            "SET del_flag = #{delFlag}, pdesc = #{pdesc}, pname = #{pname}, price = #{price} " +
            "WHERE pno = #{pno}")
    void updateProduct(Product product);

    @Delete("DELETE FROM tbl_product WHERE pno = #{pno}")
    void deleteProductById(Long pno);

    @Update("UPDATE tbl_product SET del_flag = #{delFlag} WHERE pno = #{pno}")
    void updateToDeleteProduct(long pno, boolean delFlag);

    @Insert("INSERT INTO product_image_list (product_pno, file_name, file_path, file_type, sequence) " +
            "VALUES (#{productPno}, #{fileName}, #{filePath}, #{fileType}, #{sequence})")
    @Options(useGeneratedKeys = true, keyProperty = "imageId")
    void insertProductImage(ProductImage productImage);

    @Select("SELECT * FROM product_image_list WHERE product_pno = #{productPno}")
    List<ProductImage> selectImagesByProductId(Long pno);

    @Delete("DELETE FROM product_image_list WHERE product_pno = #{productPno}")
    void deleteImagesByProductId(Long pno);

    Optional<Product> selectProductWithImagesById(Long pno);

    List<Product> selectAllProduct(@Param("sortOrder") String sortOrder,
                             @Param("offset") int offset,
                             @Param("limit") int limit);

    @Select("SELECT count(*) FROM tbl_product")
    int countTotalProduct();

}
