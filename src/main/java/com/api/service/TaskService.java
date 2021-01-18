package com.api.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.api.model.Task;
import com.api.repository.TaskRepository;

@Service
public class TaskService {

	@Autowired
	private TaskRepository taskRepository;
	
	public Task update(Long id, Task task) {
		Task taskSave = findById(id);
		BeanUtils.copyProperties(task, taskSave, "id");
		return taskRepository.save(task);
	}

	private Task findById(Long id) {
		Task taskSave = taskRepository.getOne(id);
		if (taskSave == null) {
			
			throw new EmptyResultDataAccessException(1);
		}
		return taskSave;
	}
}
