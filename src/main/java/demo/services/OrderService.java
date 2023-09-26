package demo.services;

import java.util.List;

import demo.models.Orders;
import demo.models.User;

public interface OrderService {
	List<Orders> getAll();
	List<Orders> getByUserId(Integer id);
	Boolean create(Orders a);
	Orders update(Orders a);
	Boolean delete(Integer id);
	Orders findByOrder(Integer id);
	List<Orders> getAllOrderByIdDesc();
	List<Orders> getByUserOrderByIdDesc(User user);
}
