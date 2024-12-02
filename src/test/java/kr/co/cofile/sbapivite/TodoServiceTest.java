package kr.co.cofile.sbapivite;

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
        for (int i = 1; i <= 100; i++) {

            TodoRequest todoRequest = TodoRequest.builder()
                    .title("제목..." + i)
                    .writer("user00")
                    .dueDate(LocalDate.of(2024, 12, 24))
                    .build();

            Long tno = todoService.addTodo(todoRequest);

            log.info("TNO: " + tno);
        }
    }

    @Test
    public void testFindById() {
        Long tno = 46L;

        TodoResponse todoResponse = todoService.findTodoById(tno);

        log.info(todoResponse);
    }

    @Test
    public void testUpdateTodo() {
        Long tno = 46L;
        TodoRequest todoRequest = TodoRequest.builder()
                .title("샘플 타이틀")
                .writer("홍길동")
                .build();

        todoService.modifyTodo(tno, todoRequest);
    }

    @Test
    public void testDeleteTodo() {
        Long tno = 32L;

        todoService.removeTodo(tno);

        // 런타임 예외가 발생하면 참
        assertThrows(RuntimeException.class, () -> todoService.findTodoById(tno));
    }

    @Test
    public void testListTodo() {
        PageRequest pageRequest = PageRequest.builder()
                .page(3)
                .build();
        PageResponse<TodoResponse> pageResponse = todoService.listTodo(pageRequest);

        log.info(pageResponse.toString());
    }
}
