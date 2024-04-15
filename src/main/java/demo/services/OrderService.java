package demo.services;

import java.util.List;

import org.springframework.data.domain.Page;

import demo.models.Orders;
import demo.models.User;

public interface OrderService {
	List<Orders> getAll();
	List<Orders> getByUserId(Integer id);
	Page<Orders> getByUserId(long id,Integer page,Integer limit);
	Boolean create(Orders a);
	Orders update(Orders a);
	Boolean delete(Integer id);
	Orders findById(Integer id);
	List<Orders> getAllOrderByIdDesc();
	List<Orders> getByUserIdOrderByIdDesc(long id);
	List<Orders> findByStatusAndUserIdOrderByIdDesc(Integer status,long userId);
	Page<Orders> getByStatusAndUserIdOrderByIdDesc(Integer status,long userId,Integer page,Integer limit);
	List<Orders> findByMonthAndYear(Integer month,Integer year);
}
