package demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.models.Author;
import demo.models.Publicsher;
import demo.repository.AuthorRepository;
import demo.repository.PublicsherRepository;
@Service
public class PublicsherServiceImpl implements PublicsherService{
	
	@Autowired
	private PublicsherRepository publicsherRepository;
	@Override
	public List<Publicsher> getAll() {
		// TODO Auto-generated method stub
		return this.publicsherRepository.findAll();
	}

	@Override
	public Boolean create(Publicsher a) {
		try {
			this.publicsherRepository.save(a);
			return true;
		} catch (Exception e) {
			e.printStackTrace();			
		}
		return false;
	}
	@Override
	public Boolean delete(Integer id) {
		try {
			this.publicsherRepository.delete(findById(id));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return false;
	}

	@Override
	public Publicsher findById(Integer id) {
		// TODO Auto-generated method stub
		return this.publicsherRepository.findById(id).get();
	}

}
