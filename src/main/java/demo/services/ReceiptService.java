package demo.services;

import java.util.List;

import demo.models.Author;
import demo.models.Banner;
import demo.models.Receipt;

public interface ReceiptService {
	List<Receipt> getAll();
	Boolean create(Receipt a);
	Boolean delete(Integer id);
	Receipt findById(Integer id);
	List<Receipt> findBySumMoney(long sumMoney);
}
