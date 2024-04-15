package demo.services;

import java.util.List;

import demo.models.Author;
import demo.models.Banner;
import demo.models.DetailReceipt;

public interface DetailReceiptService {
	List<DetailReceipt> getAll();
	DetailReceipt create(DetailReceipt a);
	Boolean delete(Integer id);
	DetailReceipt findById(Integer id);
	List<DetailReceipt> findByReceiptId(Integer id);
	DetailReceipt findByReceiptIdAndBookId(Integer receiptId,Integer bookId);
	DetailReceipt findByBookId(Integer bookId);
	Boolean deleteByReceiptId(Integer id);
	
}
