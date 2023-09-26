
package demo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.models.Book;
import demo.models.OrderDetail;
import demo.models.Orders;
import demo.repository.OrderDetailRepository;
@Service
public class OrderDetailServiceImpl implements OrderDetailService{
	@Autowired
	private OrderDetailRepository orderDetailRepository;
	@Autowired
	private BookService bookService;
	@Override
	public List<OrderDetail> getAll() {
		// TODO Auto-generated method stub
		return this.orderDetailRepository.findAll();
	}

	@Override
	public List<OrderDetail> getByOrderId(Integer id) {
		// TODO Auto-generated method stub
		return this.orderDetailRepository.findByOrdersId(id);
	}

	@Override
	public Boolean create(OrderDetail a) {
		try {
			this.orderDetailRepository.save(a);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return false;
	}

	@Override
	public OrderDetail findById(Integer id) {
		// TODO Auto-generated method stub
		return this.orderDetailRepository.findById(id).get();
	}

	@Override
	public Boolean deleteByOrdersId(Integer id) {
		try {
			this.orderDetailRepository.deleteByOrdersId(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<Book> findBookTrend() {
		List<Integer> listId=this.orderDetailRepository.findBookTrend();
		List<Book> listBook=new ArrayList<Book>();
		int i=0;
		for (Integer id : listId) {
			listBook.add(this.bookService.findById(id));
			i++;
			if(i==6) {
				break;
			}
		}
		return listBook;
	}



}
