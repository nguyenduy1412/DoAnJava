package demo.services;

import java.util.List;

import demo.models.Banner;

public interface BannerService {
	List<Banner> getAll();
	Boolean create(Banner a);
	Boolean update(Banner a);
	Boolean delete(Integer id);
	Banner findById(Integer id);
}
