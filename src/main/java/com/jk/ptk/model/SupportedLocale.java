package com.jk.ptk.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class SupportedLocale {
	@Id
	private String name;

	public SupportedLocale() {
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

}
