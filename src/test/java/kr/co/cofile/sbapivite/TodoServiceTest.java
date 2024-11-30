package kr.co.cofile.sbapivite;

import kr.co.cofile.sbapivite.dto.TodoRequest;
import kr.co.cofile.sbapivite.service.TodoService;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
@Log4j2
public class TodoServiceTest {

    @Autowired
    private TodoService todoService;

    @Test
    public void testRegister() {
        for (int i = 1; i <= 1; i++) {

            TodoRequest todoRequest = TodoRequest.builder()
                    .title("제목..." + i)
                    .writer("user00")
                    .dueDate(LocalDate.of(2024, 12, 24))
                    .build();

            Long tno = todoService.register(todoRequest);

            log.info("TNO: " + tno);
        }
    }
}
