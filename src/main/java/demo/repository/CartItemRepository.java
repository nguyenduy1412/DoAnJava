package demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import demo.models.CartItem;
import jakarta.transaction.Transactional;

public interface CartItemRepository extends JpaRepository<CartItem, Integer>{
	@Query("SELECT SUM(c.quantity) FROM CartItem c WHERE c.cart.id = ?1")
	Integer sumQuantityByCartId(Integer cartId);
	CartItem findByCartIdAndBookId(Integer cartId,Integer bookId);
	List<CartItem> findByCartId(Integer id);
	@Transactional
	@Modifying
	@Query("delete from CartItem i where i.cart.id =?1 ")
	void deleteByCartId(Integer id);
	List<CartItem> findByCartIdOrderByIdDesc(Integer id);
}
