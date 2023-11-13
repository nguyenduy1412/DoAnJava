package demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.models.DetailReceipt;

public interface DetailReceiptRepository extends JpaRepository<DetailReceipt, Integer>{
	List<DetailReceipt> findByReceiptIdOrderByIdDesc(Integer id);
	DetailReceipt findByReceiptIdAndBookId(Integer receiptId,Integer bookId);
	List<DetailReceipt> findByBookIdOrderByIdDesc(Integer bookId);
	
}
