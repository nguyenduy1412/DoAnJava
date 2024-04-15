package demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.models.WareHouse;
import demo.repository.WareHouseRepository;
@Service
public class WareHouseServiceImpl implements WareHouseService{
	@Autowired
	private WareHouseRepository wareHouseRepository;
	@Override
	public List<WareHouse> getAll() {
		// TODO Auto-generated method stub
		return this.wareHouseRepository.findAll();
	}

	@Override
	public Boolean create(WareHouse a) {
		try {
			this.wareHouseRepository.save(a);
			return true;
		} catch (Exception e) {
			e.printStackTrace();			
		}
		return false;
	}

	@Override
	public Boolean delete(Integer id) {
		try {
			this.wareHouseRepository.delete(findById(id));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return false;
	}

	@Override
	public WareHouse findById(Integer id) {
		// TODO Auto-generated method stub
		return this.wareHouseRepository.findById(id).get();
	}

	@Override
	public Boolean deleteByBookId(Integer id) {
		// TODO Auto-generated method stub
		try {
			this.wareHouseRepository.deleteByBookId(id);
			return true;
		} catch (Exception e) {
			e.printStackTrace();	
		}
		return false;

	}

	@Override
	public WareHouse findByBookId(Integer id) {
		// TODO Auto-generated method stub
		return this.wareHouseRepository.findByBookId(id);
	}

}
