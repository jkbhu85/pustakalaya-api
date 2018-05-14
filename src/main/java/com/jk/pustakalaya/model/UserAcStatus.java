package com.jk.pustakalaya.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="UserAccountStatus")
public class UserAcStatus {
	public static final String ACTIVE = "ACTIVE";
	public static final String LOCKED = "LOCKED";
	public static final String DELETED = "CLOSED";
	public static final String REVOKED = "REVOKED";
	public static final String INCOMPLETE = "INCOMPLETE";

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
