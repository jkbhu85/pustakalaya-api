package com.jk.pustakalaya.f.country;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@NamedQuery(name="get_all_countries", query="select c from Country c")
@Table(name="Country")
public class Country {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;

	@Column(name="name",nullable = false)
	private String name;

	@Column(name="isdCode")
	private String isdCode;

	@Column(name="abbr")
	private String abbr;

	public Country() {
	}

	public Country(String name, String isdCode, String abbr) {
		super();
		this.name = name;
		this.isdCode = isdCode;
		this.abbr = abbr;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getIsdCode() {
		return isdCode;
	}

	public void setIsdCode(String isdCode) {
		this.isdCode = isdCode;
	}

	public String getAbbr() {
		return abbr;
	}

	public void setAbbr(String abbr) {
		this.abbr = abbr;
	}

	public Long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Country [id=" + id + ", name=" + name + ", isdCode=" + isdCode + ", abbr=" + abbr + "]";
	}

}
