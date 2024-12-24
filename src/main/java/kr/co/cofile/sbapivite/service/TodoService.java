package kr.co.cofile.sbapivite.service;

import kr.co.cofile.sbapivite.dto.PageRequest;
import kr.co.cofile.sbapivite.dto.PageResponse;
import kr.co.cofile.sbapivite.dto.TodoRequest;
import kr.co.cofile.sbapivite.dto.TodoResponse;

public interface TodoService {

    Long addTodo(TodoRequest todoRequest);

    TodoResponse findTodoById(Long tno);

    void modifyTodo(Long tno, TodoRequest todoRequest);

    void removeTodo(Long tno);

    PageResponse<TodoResponse> listTodo(PageRequest pageRequest);

}