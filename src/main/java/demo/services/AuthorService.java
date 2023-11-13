package demo.services;

import java.util.List;

import demo.models.Author;
import demo.models.Banner;

public interface AuthorService {
	List<Author> getAll();
	Boolean create(Author a);
	Boolean delete(Integer id);
	Author findById(Integer id);
}
