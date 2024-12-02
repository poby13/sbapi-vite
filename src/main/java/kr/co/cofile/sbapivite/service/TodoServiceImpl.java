package kr.co.cofile.sbapivite.service;

import kr.co.cofile.sbapivite.dto.TodoRequest;
import kr.co.cofile.sbapivite.dto.TodoResponse;
import kr.co.cofile.sbapivite.entity.Todo;
import kr.co.cofile.sbapivite.mapper.TodoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
