package demo.services;

import demo.models.RequiredCancel;

public interface RequiredCancelService {
	
	Boolean create(RequiredCancel a);
	Boolean delete(Integer id);
	RequiredCancel findById(Integer id);
}
