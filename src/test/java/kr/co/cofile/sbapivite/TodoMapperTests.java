package kr.co.cofile.sbapivite;

import kr.co.cofile.sbapivite.enums.SortOrder;
import kr.co.cofile.sbapivite.dto.PageResponse;
import kr.co.cofile.sbapivite.dto.TodoResponse;
import kr.co.cofile.sbapivite.entity.Todo;
import kr.co.cofile.sbapivite.mapper.TodoMapper;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@Log4j2
public class TodoMapperTests {
    @Autowired
    private TodoMapper todoMapper;

    @Test
    public void testInsert() {
        for (int i = 1; i <= 10; i++) {

            Todo todo = Todo.builder()
                    .title("제목..." + i)
                    .writer("user00")
                    .dueDate(LocalDate.of(2024, 12, 24))
                    .build();

            todoMapper.insertTodo(todo);
        }
    }

    @Test
    public void testFindById() {
        Long tno = 25L;

        Optional<Todo> result = todoMapper.selectTodoById(tno);
        // Todo NoSuchElementException 예외처리
        Todo todo = result.orElseThrow(); // null이면 NoSuchElementException 발생

        log.info(todo);
    }

    @Test
    public void testUpdateTodo() {
        Long tno = 25L;

        Optional<Todo> result = todoMapper.selectTodoById(tno);
        Todo todo = result.orElseThrow();

        log.info(todo);

        String title = todo.getTitle();
        todo.setTitle(title + "_수정");

        todoMapper.updateTodo(todo);
    }

    @Test
    public void testDelete() {
        Long tno = 59L;
        todoMapper.deleteTodoById(tno);

        Optional<Todo> result = todoMapper.selectTodoById(tno);
        assertTrue(result.isEmpty()); // Optional이 비어 있으면 테스트 통과
    }

    @Test
    public void testPagination() {
        int currentPage = 1;
        int size = 10;
        int totalElements = todoMapper.countTotalTodo();
        String sortOrder = SortOrder.DESC.name();

        int offset = currentPage * size;
        List<Todo> todos = todoMapper.selectAllTodo(sortOrder, offset, size);

        List<TodoResponse> todoResponses = todos.stream()
                .map(todo -> TodoResponse.builder()
                        .tno(todo.getTno())
                        .title(todo.getTitle())
                        .writer(todo.getWriter())
                        .complete(todo.getComplete())
                        .dueDate(todo.getDueDate())
                        .build())
                .collect(Collectors.toList());

        PageResponse<TodoResponse> pageResponse = new PageResponse<>(todoResponses, totalElements, currentPage, size, SortOrder.valueOf(sortOrder));

        log.info(pageResponse.getTotalElements());

        pageResponse.getContent().forEach(log::info);
    }
}
