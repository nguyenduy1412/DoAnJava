package demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.models.Cart;
import demo.repository.CartRepository;
@Service
public class CartServiceImpl implements CartService {
	@Autowired
	private CartRepository cartRepository;
	@Override
	public Boolean create(Cart a) {
		try {
			this.cartRepository.save(a);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	@Override
	public Boolean checkCart(Long id) {
		
		return this.cartRepository.countByUserId(id)>0;
	}
	@Override
	public Cart findByUserId(Long id) {
		// TODO Auto-generated method stub
		return this.cartRepository.findByUserId(id);
	}
	@Override
	public Cart findById(Integer id) {
		// TODO Auto-generated method stub
		return this.cartRepository.findById(id).get();
	}

}
