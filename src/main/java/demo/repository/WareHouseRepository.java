package demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;


import demo.models.WareHouse;
import jakarta.transaction.Transactional;

public interface WareHouseRepository extends JpaRepository<WareHouse, Integer> {
	@Modifying
    @Transactional
    @Query("DELETE FROM WareHouse k WHERE k.book.id =?1")
    void deleteByBookId(Integer bookId);
	WareHouse findByBookId(Integer id);
}
