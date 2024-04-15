package demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.models.Author;
import demo.models.DetailReceipt;
import demo.repository.AuthorRepository;
import demo.repository.DetailReceiptRepository;
@Service
public class DetailReceiptServiceImpl implements DetailReceiptService{
	
	@Autowired
	private DetailReceiptRepository detailReceiptRepository;
	@Override
	public List<DetailReceipt> getAll() {
		// TODO Auto-generated method stub
		return this.detailReceiptRepository.findAll();
	}

	@Override
	public DetailReceipt create(DetailReceipt a) {
		try {
			
			return this.detailReceiptRepository.save(a);
		} catch (Exception e) {
			e.printStackTrace();			
		}
		return null;
	}
	@Override
	public Boolean delete(Integer id) {
		try {
			this.detailReceiptRepository.delete(findById(id));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return false;
	}

	@Override
	public DetailReceipt findById(Integer id) {
		// TODO Auto-generated method stub
		return this.detailReceiptRepository.findById(id).get();
	}

	@Override
	public List<DetailReceipt> findByReceiptId(Integer id) {
		// TODO Auto-generated method stub
		return this.detailReceiptRepository.findByReceiptIdOrderByIdDesc(id);
	}

	@Override
	public DetailReceipt findByReceiptIdAndBookId(Integer receiptId, Integer bookId) {
		// TODO Auto-generated method stub
		return this.detailReceiptRepository.findByReceiptIdAndBookId(receiptId, bookId);
	}

	@Override
	public DetailReceipt findByBookId(Integer bookId) {
		// TODO Auto-generated method stub
		return this.detailReceiptRepository.findByBookIdOrderByIdDesc(bookId).get(0);
	}

	@Override
	public Boolean deleteByReceiptId(Integer id) {
		try {
			this.detailReceiptRepository.deleteByReceiptId(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();	
		}
		return false;
	}

	

}
