package demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.models.Author;
import demo.models.Receipt;
import demo.repository.AuthorRepository;
import demo.repository.ReceiptRepository;
@Service
public class ReceiptServiceImpl implements ReceiptService{
	
	@Autowired
	private ReceiptRepository receiptRepository;
	@Override
	public List<Receipt> getAll() {
		// TODO Auto-generated method stub
		return this.receiptRepository.findAll();
	}

	@Override
	public Boolean create(Receipt a) {
		try {
			this.receiptRepository.save(a);
			return true;
		} catch (Exception e) {
			e.printStackTrace();			
		}
		return false;
	}
	@Override
	public Boolean delete(Integer id) {
		try {
			this.receiptRepository.delete(findById(id));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return false;
	}

	@Override
	public Receipt findById(Integer id) {
		// TODO Auto-generated method stub
		return this.receiptRepository.findById(id).get();
	}

	@Override
	public List<Receipt> findBySumMoney(long sumMoney) {
		// TODO Auto-generated method stub
		return this.receiptRepository.findBySumMoney(sumMoney);
	}

	

}
