package demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import demo.models.Banner;
import demo.repository.BannerRepository;
@Service
public class BannerServiceImpl implements BannerService{
	@Autowired
	private BannerRepository bannerRepository;
	@Override
	public List<Banner> getAll() {
		// TODO Auto-generated method stub
		return this.bannerRepository.findAll(Sort.by(Sort.Direction.ASC, "position"));
	}

	@Override
	public Boolean create(Banner a) {
		try {
			this.bannerRepository.save(a);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return false;
	}

	@Override
	public Boolean update(Banner a) {
		try {
			this.bannerRepository.save(a);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return false;
	}

	@Override
	public Boolean delete(Integer id) {
		try {
			this.bannerRepository.delete(findById(id));
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return false;
	}
	@Override
	public Banner findById(Integer id) {
		// TODO Auto-generated method stub
		return this.bannerRepository.findById(id).get();
	}

}
