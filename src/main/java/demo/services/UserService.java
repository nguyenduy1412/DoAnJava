package demo.services;

import java.util.List;

import org.springframework.data.domain.Page;

import demo.models.Category;
import demo.models.User;

public interface UserService {
	User findByUserName(String userName);
	User update(User a);
	List<User> getAll();
	User findById(Long id);
	User findByEmail(String email);
	Page<User> getAll(Integer page,Integer limit);
	List<User> searchFullName(String keyword);
	Page<User> searchFullName(String keyword,Integer page);
}
