package in.swats.springbootmongodb.service;

import in.swats.springbootmongodb.exception.TodoCollectionException;
import in.swats.springbootmongodb.model.TodoDTO;
import in.swats.springbootmongodb.repository.TodoRepository;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService{

    @Autowired
    TodoRepository todoRepository;

    @Override
    public List<TodoDTO> getAllTodos() {
        List<TodoDTO> todos =  todoRepository.findAll();
        if(todos.size() > 0) {
            return todos;
        } else {
            return new ArrayList<TodoDTO>();
        }
    }

    @Override
    public TodoDTO save(TodoDTO todoDTO) throws ConstraintViolationException, TodoCollectionException {
        Optional<TodoDTO> todoDTOOptional = todoRepository.findByTodo(todoDTO.getTodo());
        if(todoDTOOptional.isPresent()) {
            throw new TodoCollectionException(TodoCollectionException.TodoAlreadyExists());
        } else {
            todoDTO.setCreatedAt(new Date(System.currentTimeMillis()));
        }
        TodoDTO todo = todoRepository.save(todoDTO);
        return todo;
    }

    @Override
    public TodoDTO getTodoById(String id) {
        Optional<TodoDTO> todoDTOOptional =  todoRepository.findById(id);
        if(!todoDTOOptional.isPresent()) {
            throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
        } else {
          return todoDTOOptional.get();
        }
    }

    @Override
    public void deleteById(String id) {
        todoRepository.deleteById(id);
    }
}
