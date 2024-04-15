package demo.services;

import java.util.List;

import demo.models.Notification;

public interface NotificationService {
	List<Notification> findByUserIdAndOrderByIdDesc(Long id);
	Boolean create(Notification a);
	Boolean delete(Long id);
	Notification findById(Long id);
}
