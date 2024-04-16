package com.management.todo.service.impl;

import com.management.todo.dto.ToDoDTO;
import com.management.todo.entity.ToDo;
import com.management.todo.exception.ResourceNotFoundException;
import com.management.todo.repository.ToDoRepository;
import com.management.todo.service.ToDoService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class ToDoServiceImpl implements ToDoService {

    @Autowired
    private ToDoRepository toDoRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Override
    public ToDoDTO addToDo(ToDoDTO toDoDTO) {

        //convert ToDoDTO into ToDoJPA Entity
//        ToDo toDo = new ToDo();
//        toDo.setTitle(toDoDTO.getTitle());
//        toDo.setDescription(toDoDTO.getDescription());
//        toDo.setCompleted(toDoDTO.isCompleted());

        ToDo toDo = modelMapper.map(toDoDTO, ToDo.class);

        //ToDoJPA Entity
        ToDo savedToDo = toDoRepository.save(toDo);

        //convert Saved ToDoJPA Entity to ToDoDTO Object
//        ToDoDTO savedToDoDTO = new ToDoDTO();
//        savedToDoDTO.setId(savedToDo.getId());
//        savedToDoDTO.setTitle(savedToDo.getTitle());
//        savedToDoDTO.setDescription(savedToDo.getDescription());
//        savedToDoDTO.setCompleted(savedToDo.isCompleted());

        ToDoDTO savedToDoDTO = modelMapper.map(savedToDo, ToDoDTO.class);

        return savedToDoDTO;

    }

    @Override
    public ToDoDTO getTodo(Long id) {

        ToDo toDo = toDoRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Data is not present for given id: "+id));

        return modelMapper.map(toDo, ToDoDTO.class);
    }

    @Override
    public List<ToDoDTO> getAllTodos() {

        List<ToDo> allToDos = toDoRepository.findAll();

        return allToDos.stream().map(todo -> modelMapper.
                map(todo, ToDoDTO.class)).collect(Collectors.toList());
    }

    @Override
    public ToDoDTO updateToDo(Long id, ToDoDTO toDoDTO) {

        ToDo toDo = toDoRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Data are not present on given id: " + id));

        toDo.setTitle(toDoDTO.getTitle());
        toDo.setDescription(toDoDTO.getDescription());
        toDo.setCompleted(toDoDTO.isCompleted());

        ToDo updatedToDO = toDoRepository.save(toDo);

        return modelMapper.map(updatedToDO, ToDoDTO.class);
    }

    @Override
    public void deleteTodo(Long id) {
        ToDo toDo = toDoRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Data not found with given Id: " + id));
        toDoRepository.delete(toDo);

    }

    @Override
    public ToDoDTO completeTodo(Long id) {

        ToDo toDo = toDoRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Data not present with Id:" + id));
        toDo.setCompleted(Boolean.TRUE);
        ToDo updatedDTO = toDoRepository.save(toDo);

        return modelMapper.map(updatedDTO, ToDoDTO.class);
    }

    @Override
    public ToDoDTO inCompleteToDo(Long id) {

        ToDo toDo = toDoRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("Data not present with Id:" + id));
        toDo.setCompleted(Boolean.FALSE);
        ToDo savedTodo = toDoRepository.save(toDo);
        return modelMapper.map(savedTodo, ToDoDTO.class);
    }


}
