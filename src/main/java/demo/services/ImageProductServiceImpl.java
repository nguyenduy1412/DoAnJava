package demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.models.ImageProduct;
import demo.repository.ImageProductRepository;
@Service
public class ImageProductServiceImpl implements ImageProductService{

	@Autowired
	private ImageProductRepository imageProductRepository;
	@Override
	public List<ImageProduct> getAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean create(ImageProduct a) {
		// TODO Auto-generated method stub
		try {
			this.imageProductRepository.save(a);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return false;
	}

	@Override
	public Boolean update(ImageProduct a) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean delete(Integer id) {
		try {
			this.imageProductRepository.delete(findById(id));
			return true;
		} catch (Exception e) {
			e.printStackTrace();	
		}
		return false;
	}

	@Override
	public ImageProduct findById(Integer id) {
		// TODO Auto-generated method stub
		return this.imageProductRepository.findById(id).get();
	}

	@Override
	public Boolean deleteByBookId(Integer id) {
		try {
			this.imageProductRepository.deleteByBookId(id);;
			return true;
		} catch (Exception e) {
			e.printStackTrace();	
		}
		return false;
	}

}
