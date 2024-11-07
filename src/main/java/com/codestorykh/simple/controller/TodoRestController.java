package com.codestorykh.simple.controller;

import com.codestorykh.simple.dto.TodoRequest;
import com.codestorykh.simple.dto.TodoResponse;
import com.codestorykh.simple.service.TodoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController()
public class TodoRestController {

    @Autowired
    private TodoService todoService;

    @PostMapping("v1/todos")
    public ResponseEntity<Object> createTodo(@RequestBody TodoRequest todoRequest){
        log.info("Creating todo with request: {}", todoRequest);
        todoService.create(todoRequest);
        return ResponseEntity.ok().build();
    }

    @PutMapping("v1/todos/{id}")
    public ResponseEntity<Object> updateTodo(@RequestBody TodoRequest todoRequest,
                                             @PathVariable String id){
        log.info("Updating todo with id {} and request: {}", id, todoRequest);
        todoService.update(Long.parseLong(id), todoRequest);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("v1/todos/{id}")
    public ResponseEntity<?> deleteTodo(@PathVariable String id) {
        log.info("Deleting todo with id {}", id);
        todoService.delete(Long.parseLong(id));
        return ResponseEntity.accepted().build();
    }

    @GetMapping("v1/todos/{id}")
    public ResponseEntity<TodoResponse> getById(@PathVariable String id){
        log.info("Getting todo with id {}", id);
        TodoResponse todoResponse = todoService.getById(Long.parseLong(id));
        if(todoResponse == null || todoResponse.getId() == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(todoResponse);
    }

    @GetMapping("v1/todos")
    public ResponseEntity<List<TodoResponse>> getAll(){
        log.info("Getting all todos");
        List<TodoResponse> todoResponses = todoService.getAll();
        return ResponseEntity.ok(todoResponses);
    }

}
