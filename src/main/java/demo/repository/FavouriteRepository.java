package demo.repository;


import org.springframework.data.jpa.repository.JpaRepository;


import demo.models.Favourite;


public interface FavouriteRepository extends JpaRepository<Favourite, Integer> {
	Favourite findByUserId(Long id);
	long countByUserId(Long id);
}
