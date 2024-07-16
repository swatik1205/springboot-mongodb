package in.swats.springbootmongodb.advice;

import in.swats.springbootmongodb.exception.EmptyInputException;
import in.swats.springbootmongodb.exception.TodoCollectionException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.server.handler.ResponseStatusExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class MyControllerAdvice extends ResponseEntityExceptionHandler {


    @ExceptionHandler(TodoCollectionException.class)
    public ResponseEntity<String> handleEmptyInput(TodoCollectionException todoCollectionException) {
        return new ResponseEntity<String>("Input field is empty", HttpStatus.BAD_REQUEST);
    }
}
