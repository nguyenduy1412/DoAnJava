package demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.models.Author;

public interface AuthorRepository extends JpaRepository<Author, Integer> {

}
