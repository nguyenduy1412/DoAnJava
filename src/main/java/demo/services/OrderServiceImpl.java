package demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.models.Orders;
import demo.models.User;
import demo.repository.OrderRepository;
@Service
public class OrderServiceImpl implements OrderService{
	@Autowired
	private OrderRepository orderRepository;
	@Override
	public List<Orders> getAll() {
		// TODO Auto-generated method stub
		return this.orderRepository.findAll();
	}

	@Override
	public List<Orders> getByUserId(Integer id) {
		// TODO Auto-generated method stub
		return this.orderRepository.findByUserId(id);
	}

	@Override
	public Boolean create(Orders a) {
		try {
			this.orderRepository.save(a);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return false;
	}

	@Override
	public Orders findByOrder(Integer id) {
		// TODO Auto-generated method stub
		return this.orderRepository.findById(id).get();
	}

	@Override
	public Orders update(Orders a) {
		// TODO Auto-generated method stub
		return this.orderRepository.save(a);
	}

	@Override
	public List<Orders> getAllOrderByIdDesc() {
		// TODO Auto-generated method stub
		return this.orderRepository.findAllByOrderByIdDesc();
	}

	@Override
	public List<Orders> getByUserOrderByIdDesc(User user) {
		// TODO Auto-generated method stub
		return this.orderRepository.findByUserOrderByIdDesc(user);
	}

	@Override
	public Boolean delete(Integer id) {
		try {
			this.orderRepository.delete(findByOrder(id));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Orders> findByStatusAndUserIdOrderByIdDesc(Integer status,long userId) {
		return this.orderRepository.findByStatusAndUserIdOrderByIdDesc(status,userId);
	}

}
