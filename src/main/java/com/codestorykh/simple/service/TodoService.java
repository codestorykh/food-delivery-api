package com.codestorykh.simple.service;

import com.codestorykh.simple.dto.TodoRequest;
import com.codestorykh.simple.dto.TodoResponse;

import java.util.List;

public interface TodoService {

    void create(TodoRequest todoRequest);
    void update(Long id, TodoRequest todoRequest);
    void delete(Long id);
    TodoResponse getById(Long id);
    List<TodoResponse> getAll();
}
