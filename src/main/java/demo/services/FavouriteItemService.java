package demo.services;

import java.util.List;

import org.springframework.data.domain.Page;

import demo.models.FavouriteItem;

public interface FavouriteItemService {
	Boolean createOrUpdate(FavouriteItem a);
	Boolean delete(Integer id);
	FavouriteItem findById(Integer id);
	Page<FavouriteItem> findByFavouriteId(Integer id,Integer page,Integer limit);
	List<FavouriteItem> findByFavouriteIdOrderByIdDesc(Integer id);
	FavouriteItem findByFavouriteIdAndBookId(Integer favouriteId, Integer bookId);
}
