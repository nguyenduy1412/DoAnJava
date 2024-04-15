package demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import demo.models.DetailReceipt;
import jakarta.transaction.Transactional;

public interface DetailReceiptRepository extends JpaRepository<DetailReceipt, Integer>{
	List<DetailReceipt> findByReceiptIdOrderByIdDesc(Integer id);
	DetailReceipt findByReceiptIdAndBookId(Integer receiptId,Integer bookId);
	List<DetailReceipt> findByBookIdOrderByIdDesc(Integer bookId);
	@Transactional
	@Modifying
	@Query("delete from DetailReceipt i where i.receipt.id =?1 ")
	void deleteByReceiptId(Integer id);
}
