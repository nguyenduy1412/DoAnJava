package demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.models.Favourite;
import demo.repository.FavouriteRepository;

@Service
public class FavouriteServiceImpl implements FavouriteService{
	@Autowired
	private FavouriteRepository favouriteRepository;
	@Override
	public Boolean create(Favourite a) {
		try {
			this.favouriteRepository.save(a);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Boolean checkFavourite(Long id) {
		// TODO Auto-generated method stub
		return this.favouriteRepository.countByUserId(id)>0;
	}

	@Override
	public Favourite findByUserId(Long id) {
		// TODO Auto-generated method stub
		return this.favouriteRepository.findByUserId(id);
	}

	@Override
	public Favourite findById(Integer id) {
		// TODO Auto-generated method stub
		return this.favouriteRepository.findById(id).get();
	}

}
