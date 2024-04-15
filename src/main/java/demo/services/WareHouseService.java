package demo.services;

import java.util.List;

import demo.models.Author;
import demo.models.Banner;
import demo.models.WareHouse;

public interface WareHouseService {
	List<WareHouse> getAll();
	Boolean create(WareHouse a);
	Boolean delete(Integer id);
	WareHouse findById(Integer id);
	Boolean deleteByBookId(Integer id);
	WareHouse findByBookId(Integer id);
}
