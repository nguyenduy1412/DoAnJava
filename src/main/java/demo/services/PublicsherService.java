package demo.services;

import java.util.List;

import demo.models.Author;
import demo.models.Banner;
import demo.models.Publicsher;

public interface PublicsherService {
	List<Publicsher> getAll();
	Boolean create(Publicsher a);
	Boolean delete(Integer id);
	Publicsher findById(Integer id);
}
