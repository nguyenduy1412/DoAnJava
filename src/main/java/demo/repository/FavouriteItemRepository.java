package demo.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;


import demo.models.FavouriteItem;

public interface FavouriteItemRepository extends JpaRepository<FavouriteItem, Integer> {
	List<FavouriteItem> findByFavouriteId(Integer id);
	List<FavouriteItem> findByFavouriteIdOrderByIdDesc(Integer id);
	FavouriteItem findByFavouriteIdAndBookId(Integer favouriteId, Integer bookId);
}
