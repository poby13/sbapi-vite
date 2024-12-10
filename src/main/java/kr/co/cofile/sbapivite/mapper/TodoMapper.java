package kr.co.cofile.sbapivite.mapper;

import kr.co.cofile.sbapivite.entity.Todo;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface TodoMapper {

    @Insert("insert into tbl_todo (title, writer, due_date) values (#{title}, #{writer}, #{dueDate})")
    @Options(useGeneratedKeys = true, keyProperty = "tno")
    void insertTodo(Todo todo);

    @Select("select * from tbl_todo where tno = #{tno}")
    Optional<Todo> selectTodoById(Long tno);

    @Update("update tbl_todo set title = #{title}, writer = #{writer}, due_date = #{dueDate}, complete = #{complete} where tno = #{tno}")
    void updateTodo(Todo todoRes);

    @Delete("delete from tbl_todo where tno = #{tno}")
    void deleteTodoById(Long tno);

    List<Todo> selectAllTodo(@Param("sortOrder") String sortOrder,
                             @Param("offset") int offset,
                             @Param("limit") int limit);

    @Select("SELECT count(*) FROM tbl_todo")
    int countTotalTodo();
}
