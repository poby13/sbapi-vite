package kr.co.cofile.sbapivite.controller;

import kr.co.cofile.sbapivite.dto.PageRequest;
import kr.co.cofile.sbapivite.dto.PageResponse;
import kr.co.cofile.sbapivite.dto.TodoRequest;
import kr.co.cofile.sbapivite.dto.TodoResponse;
import kr.co.cofile.sbapivite.enums.SortOrder;
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

    @GetMapping("/{id}")
    public TodoResponse findTodoById(@PathVariable("id") Long id) {
        return todoService.findTodoById(id);
    }

    @GetMapping("/list")
    public PageResponse<TodoResponse> listTodo(@RequestParam(name = "page", required = false) Integer page,
                                               @RequestParam(name = "size", required = false) Integer size,
                                               @RequestParam(name = "sort_order", required = false) SortOrder sortOrder) {

        PageRequest pageRequest = PageRequest.builder()
                .page(page != null ? page : 1)
                .size(size != null ? size : 10)
                .sortOrder(sortOrder != null ? sortOrder : SortOrder.DESC)
                .build();

        return todoService.listTodo(pageRequest);
    }

    @PostMapping("/")
    public ResponseEntity<String> addTodo(@RequestBody TodoRequest todoRequest) {

        log.info("TodoRequest: {}", todoRequest);

        Long id = todoService.addTodo(todoRequest);

        return ResponseEntity.ok("TNO: {}" + id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Map<String, String>> modifyTodo(@PathVariable("id") Long id,
                                                          @RequestBody TodoRequest todoRequest) {

        log.info(todoRequest.toString());

        todoService.modifyTodo(id, todoRequest);

        Map<String, String> result = new HashMap<>();
        result.put("RESULT", "SUCCESS");

        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteTodo(@PathVariable("id") Long id) {

        log.info("Remove: {}", id);

        todoService.removeTodo(id);

        Map<String, String> result = new HashMap<>();
        result.put("RESULT", "SUCCESS");

        return ResponseEntity.ok(result);
    }
}
