package demo.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.models.RequiredCancel;
import demo.repository.RequiredCancelRepository;

@Service
public class RequiredCancelServiceImpl implements RequiredCancelService{

	@Autowired
	private RequiredCancelRepository requiredCancelRepository;
	@Override
	public Boolean create(RequiredCancel a) {
		try {
			this.requiredCancelRepository.save(a);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public Boolean delete(Integer id) {
		try {
			this.requiredCancelRepository.delete(findById(id));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public RequiredCancel findById(Integer id) {
		// TODO Auto-generated method stub
		return this.requiredCancelRepository.findById(id).get();
	}

}
