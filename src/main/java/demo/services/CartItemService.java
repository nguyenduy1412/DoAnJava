package demo.services;

import java.util.List;

import demo.models.Banner;
import demo.models.CartItem;

public interface CartItemService {
	List<CartItem> getAll();
	Boolean createOrUpdate(CartItem a);
	CartItem update(CartItem a);
	Boolean delete(Integer id);
	Boolean deleteByCartId(Integer id);
	CartItem findById(Integer id);
	Integer sumQuantityByCartId(Integer id);
	CartItem findByCartIdAndBookId(Integer cartId,Integer bookId);
	List<CartItem> findByCartId(Integer id);
	List<CartItem> findByCartIdOrderByIdDesc(Integer id);
}
