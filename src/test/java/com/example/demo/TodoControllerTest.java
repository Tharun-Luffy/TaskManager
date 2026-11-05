package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TodoController.class)
public class TodoControllerTest {
    
    @Autowired
    private MockMvc mockMvc;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    @Test
    public void testHome() throws Exception {
        mockMvc.perform(get("/api/todos/"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Todo/Task Manager")));
    }
    
    @Test
    public void testHealth() throws Exception {
        mockMvc.perform(get("/api/todos/health"))
                .andExpect(status().isOk())
                .andExpect(content().string("Todo Manager is healthy!"));
    }
    
    @Test
    public void testGetAllTodos() throws Exception {
        mockMvc.perform(get("/api/todos"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
    
    @Test
    public void testCreateTodo() throws Exception {
        Todo todo = new Todo(null, "Test Task", "Test Description", false);
        mockMvc.perform(post("/api/todos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(todo)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test Task"));
    }
}
