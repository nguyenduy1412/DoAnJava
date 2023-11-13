package demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.models.Author;
import demo.repository.AuthorRepository;
@Service
public class AuthorServiceImpl implements AuthorService{
	
	@Autowired
	private AuthorRepository authorRepository;
	@Override
	public List<Author> getAll() {
		// TODO Auto-generated method stub
		return this.authorRepository.findAll();
	}

	@Override
	public Boolean create(Author a) {
		try {
			this.authorRepository.save(a);
			return true;
		} catch (Exception e) {
			e.printStackTrace();			
		}
		return false;
	}
	@Override
	public Boolean delete(Integer id) {
		try {
			this.authorRepository.delete(findById(id));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return false;
	}

	@Override
	public Author findById(Integer id) {
		// TODO Auto-generated method stub
		return this.authorRepository.findById(id).get();
	}

}
