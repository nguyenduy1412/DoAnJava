package demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import demo.models.Book;
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
	public Orders findById(Integer id) {
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
	public List<Orders> getByUserIdOrderByIdDesc(long id) {
		// TODO Auto-generated method stub
		return this.orderRepository.findByUserIdOrderByIdDesc(id);
	}

	@Override
	public Boolean delete(Integer id) {
		try {
			this.orderRepository.delete(findById(id));
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

	@Override
	public Page<Orders> getByStatusAndUserIdOrderByIdDesc(Integer status, long userId, Integer page, Integer limit) {
		List<Orders> list = this.findByStatusAndUserIdOrderByIdDesc(status, userId);
		Pageable pageable = PageRequest.of(page - 1, limit);
		int start = (int) pageable.getOffset();
		int end = Math.min(start + pageable.getPageSize(), list.size());

		List<Orders> sublist = list.subList(start, end);

		return new PageImpl<>(sublist, pageable, list.size());
		
	}

	@Override
	public Page<Orders> getByUserId(long id, Integer page, Integer limit) {
	
		List<Orders> list = this.getByUserIdOrderByIdDesc(id);
		Pageable pageable = PageRequest.of(page - 1, limit);
		int start = (int) pageable.getOffset();
		int end = Math.min(start + pageable.getPageSize(), list.size());

		List<Orders> sublist = list.subList(start, end);

		return new PageImpl<>(sublist, pageable, list.size());
	}

	@Override
	public List<Orders> findByMonthAndYear(Integer month, Integer year) {
		// TODO Auto-generated method stub
		return this.orderRepository.findByMonthAndYear(month, year);
	}

	

}
