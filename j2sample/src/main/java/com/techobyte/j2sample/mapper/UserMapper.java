package com.techobyte.j2sample.mapper;

import java.util.List;
import com.techobyte.j2sample.model.User;

public interface UserMapper {
	public List<User> getAllUsers();
	public User getUserById(Integer id);
}
