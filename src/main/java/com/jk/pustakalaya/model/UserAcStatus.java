package com.jk.pustakalaya.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="UserAcStatus")
public class UserAcStatus {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String name;


	public UserAcStatus() {}


	public Integer getId() {
		return id;
	}


	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Account status: " + name;
	}

}
