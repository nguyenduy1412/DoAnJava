package demo.services;

import java.util.List;

import demo.models.MethodPay;

public interface MethodPayService {
	List<MethodPay> getAll();
	Boolean createOrUpdate(MethodPay a);
	Boolean delete(Integer id);
	MethodPay findById(Integer id);
}
