package com.api.service;

import javax.validation.Valid;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.api.model.User;
import com.api.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	public User update(Long id, @Valid User user) {
		User userSave = findById(id);
		BeanUtils.copyProperties(user, userSave, "id");
		return userRepository.save(user);
	}

	private User findById(Long id) {
		User userSave = userRepository.getOne(id);
		if (userSave == null) {
			throw new EmptyResultDataAccessException(1);
		}
		return userSave;
	}

}
