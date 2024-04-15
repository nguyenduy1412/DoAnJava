package demo.services;

import java.util.List;

import demo.models.Receipt;
import demo.models.Recent_Products;

public interface RecentProductService {
	List<Recent_Products> getByUser(Long id);
	Boolean create(Recent_Products a);
	Boolean delete(Integer id);
	Recent_Products findById(Integer id);
	Boolean checkProduct(Long userId,Integer bookId);
}
