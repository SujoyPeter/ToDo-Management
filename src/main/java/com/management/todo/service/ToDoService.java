package com.management.todo.service;

import com.management.todo.dto.ToDoDTO;

import java.util.List;

public interface ToDoService {

    ToDoDTO addToDo(ToDoDTO toDoDTO);

    ToDoDTO getTodo(Long id);

    List<ToDoDTO> getAllTodos();

    ToDoDTO updateToDo(Long id, ToDoDTO toDoDTO);

    void deleteTodo(Long id);

    ToDoDTO completeTodo(Long id);

    ToDoDTO inCompleteToDo(Long id);
}
