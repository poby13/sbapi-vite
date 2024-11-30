package kr.co.cofile.sbapivite.service;

import kr.co.cofile.sbapivite.dto.TodoRequest;
import kr.co.cofile.sbapivite.entity.Todo;
import kr.co.cofile.sbapivite.mapper.TodoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
