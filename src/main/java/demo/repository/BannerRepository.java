package demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.models.Banner;

public interface BannerRepository extends JpaRepository<Banner, Integer>{

}
