package demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.models.Author;
import demo.models.DiscountCode;
import demo.models.User;

public interface DiscountCodeRepository extends JpaRepository<DiscountCode, Integer> {
	DiscountCode findByNameCode(String a);
	List<DiscountCode> findByQuantityGreaterThanEqual(int quantity);
}
