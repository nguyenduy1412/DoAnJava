package demo.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import demo.models.Category;
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
	@Override
	public Page<User> getAll(Integer page,Integer limit) {
		Pageable pageable=PageRequest.of(page-1, limit);
		return this.userRepository.findAll(pageable);
	}
	@Override
	public List<User> searchFullName(String keyword) {
		// TODO Auto-generated method stub
		return this.userRepository.findByFullNameContainingIgnoreCaseOrderByFullNameAsc(keyword);
	}
	@Override
	public Page<User> searchFullName(String keyword, Integer page) {
		// TODO Auto-generated method stub
		List list=this.searchFullName(keyword);
		Pageable pageable=PageRequest.of(page-1,5);
		Integer start=(int) pageable.getOffset();
		Integer end=  (int) ( pageable.getOffset() + pageable.getPageSize()  > list.size() ? list.size() : pageable.getOffset()+pageable.getPageSize());
		list=list.subList(start, end);
		return new PageImpl<User>(list, pageable, this.searchFullName(keyword).size());
	}

}
