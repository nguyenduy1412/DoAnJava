
package demo.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

	@Override
	public Page<Book> findBookTrend(Integer page) {
		List<Book> list = this.findAllBookTrend();
		Pageable pageable = PageRequest.of(page - 1, 4);
		int start = (int) pageable.getOffset();
		int end = Math.min(start + pageable.getPageSize(), list.size());

		List<Book> sublist = list.subList(start, end);

		return new PageImpl<>(sublist, pageable, list.size());
	}

	@Override
	public List<Book> findAllBookTrend() {
		List<Integer> listId=this.orderDetailRepository.findBookTrend();
		List<Book> listBook=new ArrayList<Book>();
		
		for (Integer id : listId) {
			listBook.add(this.bookService.findById(id));
		}
		return listBook;
	}



}
