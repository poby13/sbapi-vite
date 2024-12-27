package kr.co.cofile.sbapivite.mapper;

import org.apache.ibatis.annotations.Mapper;

import kr.co.cofile.sbapivite.entity.Member;
import kr.co.cofile.sbapivite.entity.Product;
import kr.co.cofile.sbapivite.entity.ProductImage;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface MemberMapper {


    @Insert("INSERT INTO members (email, pw, nickname, social, enabled) " +
            "VALUES (#{email}, #{pw}, #{nickname}, #{social}, #enabled)")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertMember(Member member);

    @Select("SELECT * FROM members WHERE id = #{id}")
    Optional<Product> selectMemberById(Long id);

    @Update("UPDATE members " +
            "SET email = #{email}, pw = #{pw}, social = #{social}, enabled = #{enabled} " +
            "WHERE id = #{id}")
    void updateMember(Member member);

    @Delete("DELETE FROM members WHERE id = #{id}")
    void deleteMemberById(Long id);

    @Update("UPDATE members SET del_flag = #{delFlag} WHERE id = #{id}")
    void updateToDeleteMember(long id, boolean delFlag);

    @Insert("INSERT INTO member_roles (member_id, role_id) " +
            "VALUES (#{memberId}, #{roleId})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertMemberRole(long memberId, long roleId);

    @Select("SELECT * FROM member_roles WHERE member_id = #{memberId}")
    List<ProductImage> selectRolesByMemberId(Long id);

    Optional<Product> selectMemberWithRoleById(Long id);

    List<Product> selectAllMember(@Param("sortOrder") String sortOrder,
                                   @Param("offset") int offset,
                                   @Param("limit") int limit);

    @Select("SELECT count(*) FROM members")
    int countTotalMember();
}
