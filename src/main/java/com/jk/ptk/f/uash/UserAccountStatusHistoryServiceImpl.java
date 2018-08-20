package com.jk.ptk.f.uash;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * An implementation of the {@code UserAccountStatusHistoryService} type.
 *
 * @author Jitendra
 */
@Service
public class UserAccountStatusHistoryServiceImpl implements UserAccountStatusHistoryService {

	private UserAccountStatusHistoryRepository repository;

	@Autowired
	public UserAccountStatusHistoryServiceImpl(UserAccountStatusHistoryRepository repository) {
		this.repository = repository;
	}

	@Override
	@Transactional
	public void save(UserAccountStatusHistory uash) {
		repository.save(uash);
	}

	@Override
	public List<UserAccountStatusHistory> getAll(String email) {
		return repository.getAll(email);
	}

}
