package kr.co.cofile.sbapivite;

import kr.co.cofile.sbapivite.enums.SortOrder;
import kr.co.cofile.sbapivite.dto.PageRequest;
import kr.co.cofile.sbapivite.dto.PageResponse;
import kr.co.cofile.sbapivite.dto.TodoRequest;
import kr.co.cofile.sbapivite.dto.TodoResponse;
import kr.co.cofile.sbapivite.service.TodoService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
@Log4j2
public class TodoServiceTest {

    @Autowired
    private TodoService todoService;

    @Test
    public void testAddTodo() {
        for (int i = 1; i <= 50; i++) {

            TodoRequest todoRequest = TodoRequest.builder()
                    .title("제목..." + i)
                    .writer("user00")
                    .dueDate(LocalDate.of(2024, 12, 24))
                    .build();

            Long id = todoService.addTodo(todoRequest);

            log.info("ID: " + id);
        }
    }

    @Test
    public void testFindById() {
        Long id = 46L;

        TodoResponse todoResponse = todoService.findTodoById(id);

        log.info(todoResponse);
    }

    @Test
    public void testUpdateTodo() {
        Long id = 46L;
        TodoRequest todoRequest = TodoRequest.builder()
                .title("샘플 타이틀")
                .writer("홍길동")
                .complete(true)
                .build();

        todoService.modifyTodo(id, todoRequest);
    }

    @Test
    public void testDeleteTodo() {
        Long id = 80L;

        todoService.removeTodo(id);

        // 런타임 예외가 발생하면 참
        assertThrows(RuntimeException.class, () -> todoService.findTodoById(id));
    }

    @Test
    public void testListTodo() {
        PageRequest pageRequest = PageRequest.builder()
                .page(3)
                .size(10)
                .sortOrder(SortOrder.DESC)
                .build();
        PageResponse<TodoResponse> pageResponse = todoService.listTodo(pageRequest);

        log.info(pageResponse.toString());
    }
}
