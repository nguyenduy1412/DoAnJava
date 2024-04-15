package demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import demo.models.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long>{
	List<Notification> findByUserIdOrderByIdDesc(Long id);
}
