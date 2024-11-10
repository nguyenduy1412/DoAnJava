package demo.services;

import java.util.List;

import demo.models.Author;
import demo.models.Banner;
import demo.models.DiscountCode;

public interface DiscountCodeService {
	List<DiscountCode> getAll();
	Boolean create(DiscountCode a);
	Boolean delete(Integer id);
	DiscountCode findById(Integer id);
	DiscountCode findByNameCode(String a);
	List<DiscountCode> getByQuantityGreaterThanEqual(Integer quantity);
}
