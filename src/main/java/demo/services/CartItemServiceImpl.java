package demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.models.CartItem;
import demo.repository.CartItemRepository;

@Service
public class CartItemServiceImpl implements CartItemService{
	
	@Autowired
	private CartItemRepository cartItemRepository;

	@Override
	public List<CartItem> getAll() {
		// TODO Auto-generated method stub
		return this.cartItemRepository.findAll();
	}

	@Override
	public Boolean createOrUpdate(CartItem a) {
		try {
			this.cartItemRepository.save(a);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Boolean delete(Integer id) {
		try {
			this.cartItemRepository.delete(findById(id));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public CartItem findById(Integer id) {
		// TODO Auto-generated method stub
		return this.cartItemRepository.findById(id).get();
	}

	@Override
	public Integer sumQuantityByCartId(Integer id) {
		// TODO Auto-generated method stub
		return this.cartItemRepository.sumQuantityByCartId(id);
	}

	@Override
	public CartItem findByCartIdAndBookId(Integer cartId, Integer bookId) {
		// TODO Auto-generated method stub
		return this.cartItemRepository.findByCartIdAndBookId(cartId, bookId);
	}

	@Override
	public List<CartItem> findByCartId(Integer id) {
		// TODO Auto-generated method stub
		return this.cartItemRepository.findByCartId(id);
	}

	@Override
	public CartItem update(CartItem a) {
		try {
			return this.cartItemRepository.save(a);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public Boolean deleteByCartId(Integer id) {
		try {
			this.cartItemRepository.deleteByCartId(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public List<CartItem> findByCartIdOrderByIdDesc(Integer id) {
		// TODO Auto-generated method stub
		return this.cartItemRepository.findByCartIdOrderByIdDesc(id);
	}


	

}
