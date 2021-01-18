package com.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.api.event.RecursoCriadoEvent;
import com.api.model.Task;
import com.api.repository.TaskRepository;
import com.api.service.TaskService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "Task")
@RestController
@RequestMapping("/tasks")
public class TaskResource {

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private TaskService taskService;

	@Autowired
	private ApplicationEventPublisher publisher;

	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@ApiOperation("List tasks")
	@GetMapping
	public List<Task> list() {
		return taskRepository.findAll();
	}

	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@ApiOperation("Create task")
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Task> create(@Valid @RequestBody Task task, HttpServletResponse response) {
		Task taskSave = taskRepository.save(task);
		publisher.publishEvent(new RecursoCriadoEvent(this, response, taskSave.getId()));

		return ResponseEntity.status(HttpStatus.CREATED).body(taskSave);
	}

	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@ApiOperation("Delete task")
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@ApiParam(name = "Task id") @PathVariable Long id) {
		this.taskRepository.deleteById(id);
	}
 
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@ApiOperation("Update task")
	@PutMapping("/{id}")
	public ResponseEntity<Task> update(@ApiParam(name = "Task id") @PathVariable Long id,
			@Valid @RequestBody Task task) {
		Task taskSave = taskService.update(id, task);
		return ResponseEntity.ok(taskSave);
	}


	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@ApiOperation("Find task by id")
	@GetMapping("/{id]")
	public Task findById(@ApiParam(name = "Id do cliente") @PathVariable Long id) {
		return this.taskRepository.findById(id).orElse(null);
	}

}
