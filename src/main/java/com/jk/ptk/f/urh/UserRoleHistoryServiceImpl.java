package com.jk.ptk.f.urh;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * An implementation of the {@code UserRoleHistoryService} type.
 *
 * @author Jitendra
 */
@Service
public class UserRoleHistoryServiceImpl implements UserRoleHistoryService {

	private UserRoleHistoryRepositoryOrm repository;

	@Autowired
	public UserRoleHistoryServiceImpl(UserRoleHistoryRepositoryOrm repository) {
		this.repository = repository;
	}

	@Override
	@Transactional
	public void save(UserRoleHistory urh) {
		repository.save(urh);
	}

	@Override
	public List<UserRoleHistory> getAll(String email) {
		return repository.getAll(email);
	}

}
