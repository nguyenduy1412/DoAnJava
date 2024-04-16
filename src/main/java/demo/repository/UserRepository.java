package demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.models.Category;
import demo.models.User;

public interface UserRepository extends JpaRepository<User, Long>{
	User findByUserName(String userName);
	User findByEmail(String email);
	List<User> findByFullNameContainingIgnoreCaseOrderByFullNameAsc(String keyword);
}
