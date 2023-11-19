package demo.services;

import java.util.List;

import org.springframework.data.domain.Page;

import demo.models.Book;
import demo.models.OrderDetail;
import demo.models.Orders;

public interface OrderDetailService {
	List<OrderDetail> getAll();
	List<OrderDetail> getByOrderId(Integer id);
	Boolean create(OrderDetail a);
	OrderDetail findById(Integer id);
	Boolean deleteByOrdersId(Integer id);
	List<Book> findBookTrend();
	List<Book> findAllBookTrend();
	Page<Book> findBookTrend(Integer page);
}
