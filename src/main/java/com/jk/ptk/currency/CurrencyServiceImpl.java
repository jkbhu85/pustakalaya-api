package com.jk.ptk.currency;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * An implementation of the {@code CurrencyService} type. This class validates the
 * currency information before saving it if a data validator is available.
 * 
 * @author Jitendra
 */
@Service
public class CurrencyServiceImpl implements CurrencyService {
	private final CurrencyRepository repository;

	@Autowired
	public CurrencyServiceImpl(CurrencyRepository repository) {
		this.repository = repository;
	}

	@Override
	public Currency find(String id) {
		if (id == null || id.isEmpty()) return null;

		try {
			Integer cid = Integer.parseInt(id);
			return repository.find(cid);
		} catch (NumberFormatException ignore) {}

		return null;
	}

	@Override
	public List<Currency> getAll() {
		return repository.getAll();
	}

	@Override
	public boolean doesCurrencyExist(String id) {
		if (id == null || id.isEmpty()) return false;

		try {
			Integer cid = Integer.parseInt(id);
			return repository.doesCurrencyExist(cid);
		} catch (NumberFormatException ignore) {}

		return false;
	}

}
