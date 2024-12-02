package kr.co.cofile.sbapivite.controller;

import kr.co.cofile.sbapivite.dto.PageRequest;
import kr.co.cofile.sbapivite.dto.PageResponse;
import kr.co.cofile.sbapivite.dto.TodoRequest;
import kr.co.cofile.sbapivite.dto.TodoResponse;
import kr.co.cofile.sbapivite.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/todo")
public class TodoController {

    private final TodoService todoService;

    @GetMapping("/{tno}")
    public TodoResponse findTodoById(@PathVariable("tno") Long tno) {
        return todoService.findTodoById(tno);
    }

    @GetMapping("/list")
    public PageResponse<TodoResponse> listTodo(@RequestBody PageRequest pageRequest) {
        return todoService.listTodo(pageRequest);
    }

    @PostMapping("/")
    public ResponseEntity<String> addTodo(@RequestBody TodoRequest todoRequest) {

        log.info("TodoRequest: {}", todoRequest);

        Long tno = todoService.addTodo(todoRequest);

        return ResponseEntity.ok("TNO: {}" + tno);
    }

    @PutMapping("/{tno}")
    public ResponseEntity<Map<String, String>> modifyTodo(@PathVariable("tno") Long tno,
                                             @RequestBody TodoRequest todoRequest) {

        log.info(todoRequest.toString());

        todoService.modifyTodo(tno, todoRequest);

        Map<String, String> result = new HashMap<>();
        result.put("RESULT", "SUCCESS");

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{tno}")
    public ResponseEntity<Map<String, String>> deleteTodo(@PathVariable("tno") Long tno) {

        log.info("Remove: {}", tno);

        todoService.removeTodo(tno);

        Map<String, String> result = new HashMap<>();
        result.put("RESULT", "SUCCESS");

        return ResponseEntity.ok(result);
    }
}
