package demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import demo.models.Book;
import demo.models.CartItem;
import demo.models.FavouriteItem;
import demo.repository.CartItemRepository;
import demo.repository.FavouriteItemRepository;
import demo.repository.FavouriteRepository;

@Service
public class FavouriteItemServiceImpl implements FavouriteItemService{
	
	@Autowired
	private FavouriteItemRepository favouriteItemRepository;

	

	@Override
	public Boolean createOrUpdate(FavouriteItem a) {
		try {
			this.favouriteItemRepository.save(a);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public Boolean delete(Integer id) {
		try {
			this.favouriteItemRepository.delete(findById(id));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public FavouriteItem findById(Integer id) {
		// TODO Auto-generated method stub
		return this.favouriteItemRepository.findById(id).get();
	}

	@Override
	public Page<FavouriteItem> findByFavouriteId(Integer id, Integer page, Integer limit) {
		List<FavouriteItem> list = this.findByFavouriteIdOrderByIdDesc(id);
		Pageable pageable = PageRequest.of(page - 1, limit);
		int start = (int) pageable.getOffset();
		int end = Math.min(start + pageable.getPageSize(), list.size());

		List<FavouriteItem> sublist = list.subList(start, end);

		return new PageImpl<>(sublist, pageable, list.size());
	}

	@Override
	public List<FavouriteItem> findByFavouriteIdOrderByIdDesc(Integer id) {
		// TODO Auto-generated method stub
		return this.favouriteItemRepository.findByFavouriteIdOrderByIdDesc(id);
	}

	@Override
	public FavouriteItem findByFavouriteIdAndBookId(Integer favouriteId, Integer bookId) {
		return this.favouriteItemRepository.findByFavouriteIdAndBookId(favouriteId, bookId);
	}



}
