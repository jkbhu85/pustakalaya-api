package com.jk.ptk.f.bookinstance;

import java.util.Map;

import org.springframework.stereotype.Service;

@Service
public class BookInstanceServiceImpl implements BookInstanceService {
	private BookInstanceRepository repository;

	@Override
	public Map<String, String> findBookInstance(String bookInstanceId) {
		Map<String, String> map = null;
		Long id;
		
		try {
			id = Long.parseLong(bookInstanceId);
		} catch (NumberFormatException ignore) {
			return null;
		}
		
		BookInstance bookInstance = repository.find(id);
		
		return map;
	}

	@Override
	public void assignBookInstance(String bookInstanceId, String email) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void unassignBookInstance(String bookInstanceId, String amountFined) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateBookInstanceStatus(String bookInstanceId, String newStatus) {
		// TODO Auto-generated method stub
		
	}

}
