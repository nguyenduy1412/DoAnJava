package demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.models.Author;
import demo.models.Receipt;
import demo.models.Recent_Products;
import demo.repository.AuthorRepository;
import demo.repository.ReceiptRepository;
import demo.repository.RecentProductRepository;
@Service
public class RecentProductServiceImpl implements RecentProductService{

	@Autowired
	private RecentProductRepository recentProductRepository;
	@Override
	public List<Recent_Products> getByUser(Long id) {
		// TODO Auto-generated method stub
		return this.recentProductRepository.findTop4ByUserIdOrderByIdDesc(id);
	}

	@Override
	public Boolean create(Recent_Products a) {
		try {
			this.recentProductRepository.save(a);
			return true;
		} catch (Exception e) {
			e.printStackTrace();	
		}
		return false;
	}

	@Override
	public Boolean delete(Integer id) {
		try {
			this.recentProductRepository.delete(findById(id));
			return true;
		} catch (Exception e) {
			e.printStackTrace();	
		}
		return false;
	}

	@Override
	public Recent_Products findById(Integer id) {
		// TODO Auto-generated method stub
		return this.recentProductRepository.findById(id).get();
	}

	@Override
	public Boolean checkProduct(Long userId, Integer bookId) {
		// TODO Auto-generated method stub
		List<Recent_Products> kq=this.recentProductRepository.findByUserIdAndBookId(userId, bookId);
		for (Recent_Products recent_Products : kq) {
			System.out.println("Check list"+recent_Products.getBook());
		}
		if(kq.size()>0)
			return true;
		return false;
	}
	
	

}
