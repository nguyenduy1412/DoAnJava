package demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.models.Receipt;

public interface ReceiptRepository extends JpaRepository<Receipt, Integer>{

}
