package in.swats.springbootmongodb;

import in.swats.springbootmongodb.model.TodoDTO;
import in.swats.springbootmongodb.repository.TodoRepository;
import in.swats.springbootmongodb.service.TodoService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Date;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.mockito.Mockito.*;

@SpringBootTest
class SpringbootmongodbApplicationTests {

	@Autowired
	private TodoService todoService;

	@MockBean
	private TodoRepository repository;

	@Test
	public void getAllTodosTest() {
		when(repository.findAll()).thenReturn(Stream
				.of(new TodoDTO("123","book tickets","book tain tickets",true,new Date(),new Date())).collect(Collectors.toList()));
		Assert.assertEquals(1, todoService.getAllTodos().size());
	}

	@Test
	public void getTodoByIdTest() {
		String id = "123";
		when(repository.findById(id)).thenReturn(Stream
				.of(new TodoDTO("123","book tickets","book tain tickets",true,new Date(),new Date())).findFirst());
		Assert.assertEquals("book tickets", todoService.getTodoById(id).getTodo());
	}

	@Test
	public void createTodoTest() {
		TodoDTO todoDTO = new TodoDTO("123","book tickets","book tain tickets",true,new Date(),new Date());
		when(repository.save(todoDTO)).thenReturn(todoDTO);
		Assert.assertEquals(todoDTO, todoService.save(todoDTO));
	}

	@Test
	public void deleteTodoTest() {
		String id = "123";
		TodoDTO todoDTO = new TodoDTO("123","book tickets","book tain tickets",true,new Date(),new Date());
		todoService.deleteById(id);
		verify(repository, times(1)).deleteById(id);
	}

}
