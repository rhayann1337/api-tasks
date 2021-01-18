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
import com.api.model.User;
import com.api.repository.UserRepository;
import com.api.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "User")
@RestController
@RequestMapping("/users")
public class UserResource {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ApplicationEventPublisher publisher;
	
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@ApiOperation("List users")
	@GetMapping
	public List<User> list(){
		return userRepository.findAll();
	}
	
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@ApiOperation("Create user")
	@PostMapping
	public ResponseEntity<User> create(@Valid @RequestBody User user, HttpServletResponse response){
		User userSave = userRepository.save(user);
		
		publisher.publishEvent(new RecursoCriadoEvent(this, response, userSave.getId()));
		
		return ResponseEntity.status(HttpStatus.CREATED).body(userSave);
	}
	
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@ApiOperation("Delete user")
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delete(@ApiParam(name = "User id") @PathVariable Long id) {
		this.userRepository.deleteById(id);
	}
	
	
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@ApiOperation("Update user")
	@PutMapping("/{id}")
	public ResponseEntity<User> update(@ApiParam(name = "User id") @PathVariable Long id,
			@Valid @RequestBody User user){
		User userSave = userService.update(id, user);
		return ResponseEntity.ok(userSave);
	}
	
	@ApiImplicitParam(name = "Authorization", value = "Bearer Token", required = true, allowEmptyValue = false, paramType = "header", example = "Bearer access_token")
	@ApiOperation("Find user by id")
	@GetMapping("/{id]")
	public User findById(@ApiParam(name = "Id do cliente") @PathVariable Long id) {
		return this.userRepository.findById(id).orElse(null);
	}
	
}	
