package demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.models.Author;
import demo.models.DiscountCode;
import demo.repository.AuthorRepository;
import demo.repository.DiscountCodeRepository;
@Service
public class DiscountCodeServiceImpl implements DiscountCodeService{
	
	@Autowired
	private DiscountCodeRepository discountCodeRepository;
	@Override
	public List<DiscountCode> getAll() {
		// TODO Auto-generated method stub
		return this.discountCodeRepository.findAll();
	}

	@Override
	public Boolean create(DiscountCode a) {
		try {
			this.discountCodeRepository.save(a);
			return true;
		} catch (Exception e) {
			e.printStackTrace();			
		}
		return false;
	}
	@Override
	public Boolean delete(Integer id) {
		try {
			this.discountCodeRepository.delete(findById(id));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return false;
	}

	@Override
	public DiscountCode findById(Integer id) {
		// TODO Auto-generated method stub
		return this.discountCodeRepository.findById(id).get();
	}

	@Override
	public DiscountCode findByNameCode(String a) {
		// TODO Auto-generated method stub
		return this.discountCodeRepository.findByNameCode(a);
	}

	@Override
	public List<DiscountCode> getByQuantityGreaterThanEqual(Integer quantity) {
		// TODO Auto-generated method stub
		return this.discountCodeRepository.findByQuantityGreaterThanEqual(quantity);
	}

	

	
}
