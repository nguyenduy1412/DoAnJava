package demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.models.Notification;
import demo.repository.NotificationRepository;

@Service
public class NotificationServiceImpl implements NotificationService{

	@Autowired
	private NotificationRepository notificationRepository;
	
	@Override
	public Boolean create(Notification a) {
		try {
			this.notificationRepository.save(a);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Boolean delete(Long id) {
		try {
			this.notificationRepository.delete(findById(id));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Notification findById(Long id) {
		// TODO Auto-generated method stub
		return this.notificationRepository.findById(id).get();
	}

	@Override
	public List<Notification> findByUserIdAndOrderByIdDesc(Long id) {
		// TODO Auto-generated method stub
		return this.notificationRepository.findByUserIdOrderByIdDesc(id);
	}

}
