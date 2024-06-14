package in.swats.springbootmongodb.controller;

import in.swats.springbootmongodb.exception.TodoCollectionException;
import in.swats.springbootmongodb.model.TodoDTO;
import in.swats.springbootmongodb.service.TodoService;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todomanageapi")
public class TodoController {

    @Autowired
    private TodoService todoService;

    @GetMapping("/todos")
    public ResponseEntity<?> getAllTodos() {
        List<TodoDTO> todos = todoService.getAllTodos();
        return new ResponseEntity<>(todos, todos.size() > 0 ?HttpStatus.OK : HttpStatus.NOT_FOUND);
    }

    @PostMapping("/todos")
    public ResponseEntity<?> createTodo(@RequestBody TodoDTO todoDTO) {
        try {
            todoService.save(todoDTO);
            return new ResponseEntity<TodoDTO>(todoDTO, HttpStatus.OK);
        } catch (ConstraintViolationException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        } catch (TodoCollectionException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
        }
    }

    @GetMapping("todos/{id}")
    public ResponseEntity<?> getTodoById(@PathVariable("id") String id) {
       try {
            return new ResponseEntity<TodoDTO>(todoService.getTodoById(id), HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<>("Todo not found with id "+id, HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("todos/{id}")
    public ResponseEntity<?> updateTodoById(@PathVariable("id") String id, @RequestBody TodoDTO todoDTO) {
        TodoDTO todoToUpdate = todoService.getTodoById(id);
        try {
            todoToUpdate.setCompleted(todoDTO.getCompleted() != null ? todoDTO.getCompleted() : todoToUpdate.getCompleted());
            todoToUpdate.setTodo(todoDTO.getTodo() != null ? todoDTO.getTodo() : todoToUpdate.getTodo());
            todoToUpdate.setDescription(todoDTO.getDescription() != null ? todoDTO.getDescription() : todoToUpdate.getDescription());
            todoToUpdate.setUpdatedAt(todoDTO.getUpdatedAt() != null ? todoDTO.getUpdatedAt() : todoToUpdate.getUpdatedAt());
            todoService.save(todoToUpdate);
            return new ResponseEntity<TodoDTO>(todoToUpdate, HttpStatus.OK);
        } catch (Exception ex){
            return new ResponseEntity<>("Todo not found with id "+id, HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("todos/{id}")
    public ResponseEntity<?> deleteById(@PathVariable("id") String id) {
        TodoDTO todoToDelete = todoService.getTodoById(id);
        try {
            if(todoToDelete != null) {
                todoService.deleteById(id);
                return new ResponseEntity<>("Successfully deleted the record",HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Todo not found with id "+id, HttpStatus.NOT_FOUND);
            }
            } catch (Exception ex) {
            return new ResponseEntity<>("Todo could not deleted", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
