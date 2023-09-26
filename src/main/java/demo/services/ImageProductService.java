package demo.services;

import java.util.List;

import demo.models.Category;
import demo.models.ImageProduct;

public interface ImageProductService {
	List<ImageProduct> getAll();
	Boolean create(ImageProduct a);
	Boolean update(ImageProduct a);
	Boolean delete(Integer id);
	ImageProduct findById(Integer id);
	Boolean deleteByBookId(Integer id);
}
