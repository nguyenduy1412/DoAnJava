package demo.services;

import java.util.List;

import demo.models.Category;
import demo.models.User;

public interface UserService {
	User findByUserName(String userName);
	User update(User a);
	List<User> getAll();
	User findById(Long id);
	User findByEmail(String email);
}
