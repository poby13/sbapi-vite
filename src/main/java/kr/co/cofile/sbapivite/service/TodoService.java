package kr.co.cofile.sbapivite.service;

import kr.co.cofile.sbapivite.dto.PageRequest;
import kr.co.cofile.sbapivite.dto.TodoRequest;
import kr.co.cofile.sbapivite.dto.TodoResponse;

import java.util.List;

public interface TodoService {

    Long register(TodoRequest todoRequest);
    TodoResponse findTodoById(Long tno);
    void modifyTodo(Long tno, TodoRequest todoRequest);
    void removeTodo(Long tno);
    List<TodoResponse> listTodo(PageRequest pageRequest);

}
