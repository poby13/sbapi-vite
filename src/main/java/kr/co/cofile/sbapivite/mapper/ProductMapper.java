package kr.co.cofile.sbapivite.mapper;

import kr.co.cofile.sbapivite.entity.Product;
import kr.co.cofile.sbapivite.entity.ProductImage;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ProductMapper {

    @Insert("INSERT INTO products (del_flag, pdesc, pname, price) " +
            "VALUES (#{delFlag}, #{pdesc}, #{pname}, #{price})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertProduct(Product product);

    @Select("SELECT * FROM products WHERE id = #{id}")
    Optional<Product> selectProductById(Long id);

    @Update("UPDATE products " +
            "SET del_flag = #{delFlag}, pdesc = #{pdesc}, pname = #{pname}, price = #{price} " +
            "WHERE id = #{id}")
    void updateProduct(Product product);

    @Delete("DELETE FROM products WHERE id = #{id}")
    void deleteProductById(Long id);

    @Update("UPDATE products SET del_flag = #{delFlag} WHERE id = #{id}")
    void updateToDeleteProduct(long id, boolean delFlag);

    @Insert("INSERT INTO product_images (product_id, file_name, file_path, file_type, thumbnail_path, sequence) " +
            "VALUES (#{productId}, #{fileName}, #{filePath}, #{fileType}, #{thumbnailPath}, #{sequence})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertProductImage(ProductImage productImage);

    @Select("SELECT * FROM product_images WHERE product_id = #{productId}")
    List<ProductImage> selectImagesByProductId(Long id);

    @Delete("DELETE FROM product_images WHERE product_id = #{productId}")
    void deleteImagesByProductId(Long id);

    Optional<Product> selectProductWithImagesById(Long id);

    List<Product> selectAllProduct(@Param("sortOrder") String sortOrder,
                                   @Param("offset") int offset,
                                   @Param("limit") int limit);

    @Select("SELECT count(*) FROM products")
    int countTotalProduct();

}
