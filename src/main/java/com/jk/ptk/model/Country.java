package com.jk.ptk.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Represents country in database.
 * 
 * @author Jitendra
 *
 */
@Entity
@Table(name="Country")
public class Country {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String name;
	private String isdCode;
	private String abbreviation;
	
	public Country() {
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getIsdCode() {
		return isdCode;
	}

	public String getAbbreviation() {
		return abbreviation;
	}
	
}
