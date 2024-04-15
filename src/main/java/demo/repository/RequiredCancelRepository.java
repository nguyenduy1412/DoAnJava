package demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.models.RequiredCancel;

public interface RequiredCancelRepository extends JpaRepository<RequiredCancel, Integer>{

}
