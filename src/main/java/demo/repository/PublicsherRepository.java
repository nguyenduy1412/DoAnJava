package demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import demo.models.Publicsher;

public interface PublicsherRepository extends JpaRepository<Publicsher, Integer> {

}
