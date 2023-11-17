package demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import demo.models.User;
import demo.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService{
	@Autowired
	private UserRepository userRepository;
	@Override
	public User findByUserName(String userName) {
		// TODO Auto-generated method stub
		return userRepository.findByUserName(userName);
	}
	@Override
	public User update(User a) {
		try {
			
			return this.userRepository.save(a);
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return null;
	}
	@Override
	public List<User> getAll() {
		// TODO Auto-generated method stub
		return this.userRepository.findAll();
	}
	@Override
	public User findById(Long id) {
		// TODO Auto-generated method stub
		return this.userRepository.findById(id).get();
	}
	@Override
	public User findByEmail(String email) {
		// TODO Auto-generated method stub
		return this.userRepository.findByEmail(email);
	}

}
