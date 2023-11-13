package demo.services;

import demo.models.Cart;
import demo.models.Favourite;

public interface FavouriteService {
	Boolean create(Favourite a);
	Boolean checkFavourite(Long id);
	Favourite findByUserId(Long id);
	Favourite findById(Integer id);
}
