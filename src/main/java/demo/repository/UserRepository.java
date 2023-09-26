package demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.models.User;

public interface UserRepository extends JpaRepository<User, Long>{
	User findByUserName(String userName);
}
