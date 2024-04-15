package demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.models.MethodPay;
import demo.repository.MethodPayRepository;
@Service
public class MethodPayServiceImpl implements MethodPayService{

	@Autowired
	private MethodPayRepository methodPayRepository;
	@Override
	public List<MethodPay> getAll() {
		// TODO Auto-generated method stub
		return this.methodPayRepository.findAll();
	}

	@Override
	public Boolean createOrUpdate(MethodPay a) {
		try {
			this.methodPayRepository.save(a);
			return true;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}

	@Override
	public Boolean delete(Integer id) {
		try {
			this.methodPayRepository.delete(findById(id));
			return true;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return false;
	}

	@Override
	public MethodPay findById(Integer id) {
		// TODO Auto-generated method stub
		return this.methodPayRepository.findById(id).get();
	}

}
