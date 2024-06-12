package in.swats.springbootmongodb.service;

import in.swats.springbootmongodb.model.TodoDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface TodoService {
    List<TodoDTO> getAllTodos();

    TodoDTO save(TodoDTO todoDTO);

    TodoDTO getTodoById(String id);

    void deleteById(String id);
}
