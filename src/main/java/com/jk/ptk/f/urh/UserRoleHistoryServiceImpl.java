package com.jk.ptk.f.urh;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleHistoryServiceImpl implements UserRoleHistoryService {
	
	private UserRoleHistoryRepository repository;

	@Autowired
	public UserRoleHistoryServiceImpl(UserRoleHistoryRepository repository) {
		this.repository = repository;
	}

	@Override
	@Transactional
	public void addRoleHistory(UserRoleHistory urh) {
		if (urh == null) return;
		
		repository.addRoleHistory(urh);
	}

	@Override
	public List<UserRoleHistory> getRoleHistory(String email) {
		if (email == null) return new ArrayList<>();
		
		return repository.getRoleHistory(email);
	}

}
