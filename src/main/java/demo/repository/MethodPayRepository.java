package demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.models.MethodPay;

public interface MethodPayRepository extends JpaRepository<MethodPay, Integer>{

}
