package com.codestorykh.simple.service.impl;

import com.codestorykh.simple.dto.TodoRequest;
import com.codestorykh.simple.dto.TodoResponse;
import com.codestorykh.simple.model.Todo;
import com.codestorykh.simple.repository.TodoRepository;
import com.codestorykh.simple.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }


    @Override
    public void create(TodoRequest todoRequest) {

        Todo todo = new Todo();
        todo.setTitle(todoRequest.getTitle());
        todo.setDescription(todoRequest.getDescription());
        todo.setCompleted(false);
        todo.setCreatedAt(new Date());

        todoRepository.save(todo);
    }

    @Override
    public void update(Long id, TodoRequest todoRequest) {

        Optional<Todo> todo =  todoRepository.findById(id);
        if(todo.isEmpty()){
            log.info("Todo with id {} not found", id);
            return;
        }

        Todo todoToUpdate = todo.get();
        todoToUpdate.setTitle(todoRequest.getTitle());
        todoToUpdate.setDescription(todoRequest.getDescription());
        todoToUpdate.setCompleted(true);
        todoToUpdate.setUpdatedAt(new Date());

        todoRepository.saveAndFlush(todoToUpdate);
    }

    @Override
    public void delete(Long id) {
        todoRepository.deleteById(id);
    }

    @Override
    public TodoResponse getById(Long id) {
        TodoResponse todoResponse = new TodoResponse();

        Optional<Todo> todo = todoRepository.findById(id);
        if(todo.isEmpty()){
            log.info("Get Todo with id {} not found", id);
            return todoResponse;
        }
        todoResponse.setId(todo.get().getId());
        todoResponse.setTitle(todo.get().getTitle());
        todoResponse.setDescription(todo.get().getDescription());
        todoResponse.setCompleted(todo.get().isCompleted());
        return todoResponse;
    }

    @Override
    public List<TodoResponse> getAll() {

        List<TodoResponse> todoResponses = new ArrayList<>();

        List<Todo> todo = todoRepository.findAll();
        if(todo.isEmpty()){
            log.info("No Todo found");
            return todoResponses;
        }

        for(var t : todo){
            TodoResponse todoResponse = new TodoResponse();
            todoResponse.setId(t.getId());
            todoResponse.setTitle(t.getTitle());
            todoResponse.setDescription(t.getDescription());
            todoResponse.setCompleted(t.isCompleted());

            todoResponses.add(todoResponse);
        }

        return todoResponses;
    }

}
