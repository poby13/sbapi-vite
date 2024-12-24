package kr.co.cofile.sbapivite.mapper;

import kr.co.cofile.sbapivite.entity.Todo;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface TodoMapper {

    @Insert("insert into todos (title, writer, due_date) values (#{title}, #{writer}, #{dueDate})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void insertTodo(Todo todo);

    @Select("select * from todos where id = #{id}")
    Optional<Todo> selectTodoById(Long id);

    @Update("update todos set title = #{title}, writer = #{writer}, due_date = #{dueDate}, complete = #{complete} where id = #{id}")
    void updateTodo(Todo todoRes);

    @Delete("delete from todos where id = #{id}")
    void deleteTodoById(Long id);

    List<Todo> selectAllTodo(@Param("sortOrder") String sortOrder,
                             @Param("offset") int offset,
                             @Param("limit") int limit);

    @Select("SELECT count(*) FROM todos")
    int countTotalTodo();
}
