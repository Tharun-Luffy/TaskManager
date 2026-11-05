package com.example.demo;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@RestController
@RequestMapping("/api/todos")
public class TodoController {
    
    private final List<Todo> todoList = new ArrayList<>();
    private final AtomicLong counter = new AtomicLong();
    
    public TodoController() {
        // Add some sample todos
        todoList.add(new Todo(counter.incrementAndGet(), "Setup CI/CD Pipeline", "Complete CIA 2 assignment", false));
        todoList.add(new Todo(counter.incrementAndGet(), "Learn Docker", "Containerize applications", false));
        todoList.add(new Todo(counter.incrementAndGet(), "Learn Kubernetes", "Deploy to K8s cluster", false));
    }
    
    @GetMapping("/")
    public String home() {
        return "Todo/Task Manager Application - Powered by CI/CD Pipeline";
    }
    
    @GetMapping("/health")
    public String health() {
        return "Todo Manager is healthy!";
    }
    
    @GetMapping
    public ResponseEntity<List<Todo>> getAllTodos() {
        return new ResponseEntity<>(todoList, HttpStatus.OK);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable Long id) {
        return todoList.stream()
                .filter(todo -> todo.getId().equals(id))
                .findFirst()
                .map(todo -> new ResponseEntity<>(todo, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    
    @PostMapping
    public ResponseEntity<Todo> createTodo(@RequestBody Todo todo) {
        todo.setId(counter.incrementAndGet());
        todoList.add(todo);
        return new ResponseEntity<>(todo, HttpStatus.CREATED);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Todo> updateTodo(@PathVariable Long id, @RequestBody Todo updatedTodo) {
        for (int i = 0; i < todoList.size(); i++) {
            Todo todo = todoList.get(i);
            if (todo.getId().equals(id)) {
                updatedTodo.setId(id);
                todoList.set(i, updatedTodo);
                return new ResponseEntity<>(updatedTodo, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @PatchMapping("/{id}/complete")
    public ResponseEntity<Todo> markComplete(@PathVariable Long id) {
        for (Todo todo : todoList) {
            if (todo.getId().equals(id)) {
                todo.setCompleted(true);
                return new ResponseEntity<>(todo, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTodo(@PathVariable Long id) {
        boolean removed = todoList.removeIf(todo -> todo.getId().equals(id));
        if (removed) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
