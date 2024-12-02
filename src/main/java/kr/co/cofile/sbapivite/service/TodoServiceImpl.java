package kr.co.cofile.sbapivite.service;

import kr.co.cofile.sbapivite.SortOrder;
import kr.co.cofile.sbapivite.dto.PageRequest;
import kr.co.cofile.sbapivite.dto.PageResponse;
import kr.co.cofile.sbapivite.dto.TodoRequest;
import kr.co.cofile.sbapivite.dto.TodoResponse;
import kr.co.cofile.sbapivite.entity.Todo;
import kr.co.cofile.sbapivite.mapper.TodoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Log4j2
@RequiredArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final ModelMapper modelMapper;
    private final TodoMapper todoMapper;

    @Override
    public Long register(TodoRequest todoRequest) {
        log.info("register");

        // DTO를 엔티티로 변환
        Todo todo = modelMapper.map(todoRequest, Todo.class);

        todoMapper.insertTodo(todo);

        return todo.getTno();
    }

    @Override
    public TodoResponse findTodoById(Long tno) {

        Optional<Todo> result = todoMapper.selectTodoById(tno);
        Todo todo = result.orElseThrow();

        return modelMapper.map(todo, TodoResponse.class);
    }

    @Override
    public void modifyTodo(Long tno, TodoRequest todoRequest) {

        Todo todo = modelMapper.map(todoRequest, Todo.class);
        todo.setTno(tno);

        todoMapper.updateTodo(todo);
    }

    @Override
    public void removeTodo(Long tno) {

        todoMapper.deleteTodoById(tno);
    }

    @Override
    public PageResponse<TodoResponse> listTodo(PageRequest pageRequest) {

        List<Todo> todos = todoMapper.selectAllTodo(SortOrder.DESC, pageRequest.getOffset(), pageRequest.getSize());
        List<TodoResponse> todoResponses =  todos.stream()
                .map(todo -> modelMapper.map(todo, TodoResponse.class))
                .toList();

        int totalElements = todoMapper.countTotalTodo();
        int currentPage = pageRequest.getPage();
        int size = pageRequest.getSize();
        SortOrder sortOrder = pageRequest.getSortOrder();

        return new PageResponse<>(todoResponses, totalElements, currentPage, size, sortOrder);
    }
}
