package demo.services;

import java.util.List;

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
}
