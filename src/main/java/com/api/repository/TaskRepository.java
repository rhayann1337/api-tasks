package com.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.model.Task;

public interface TaskRepository extends JpaRepository<Task, Long>{
	
	List<Task> findByNameContaining(String name);
}
