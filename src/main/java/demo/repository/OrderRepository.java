package demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.models.Orders;
import demo.models.User;

public interface OrderRepository extends JpaRepository<Orders, Integer>{
	List<Orders> findByUserId(Integer id);
	List<Orders> findAllByOrderByIdDesc();
	List<Orders> findByUserOrderByIdDesc(User user);
	List<Orders> findByStatusAndUserIdOrderByIdDesc(Integer status,long userId);
}
