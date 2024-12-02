package kr.co.cofile.sbapivite.controller;

import kr.co.cofile.sbapivite.dto.PageRequest;
import kr.co.cofile.sbapivite.dto.PageResponse;
import kr.co.cofile.sbapivite.dto.TodoResponse;
import kr.co.cofile.sbapivite.service.TodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
