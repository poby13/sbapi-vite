package kr.co.cofile.sbapivite.service;

import kr.co.cofile.sbapivite.dto.TodoRequest;

public interface TodoService {
    Long register(TodoRequest todoRequest);
}
