package demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import demo.models.Receipt;
import demo.models.Recent_Products;
import jakarta.transaction.Transactional;

public interface ReceiptRepository extends JpaRepository<Receipt, Integer>{
	 List<Receipt> findBySumMoney(long sumMoney);
}
