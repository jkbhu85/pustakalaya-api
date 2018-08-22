package com.jk.ptk.currency;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * API end point for the type {@code Currency}.
 * 
 * @author Jitendra
 */
@RestController
@RequestMapping("/ptk/currency")
public class CurrencyController {
	private final CurrencyService service;

	/**
	 * Creates an instance with the specified currency service.
	 * 
	 * @param service
	 *                a currency service
	 */
	public CurrencyController(CurrencyService service) {
		this.service = service;
	}

	@GetMapping("/{currencyId}")
	public Currency find(@PathVariable("currencyId") String currencyId) {
		Currency currency = null;
		try {
			currency = this.service.find(currencyId);
		} catch (Exception e) {}

		return currency;
	}

	@GetMapping
	public List<Currency> getAll() {
		List<Currency> list = null;
		try {
			list = this.service.getAll();
		} catch (Exception e) {}

		return list;
	}
}
