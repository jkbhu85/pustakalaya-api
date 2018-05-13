package com.jk.pustakalaya.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Represents user roles used in the application.
 * @author Jitendra
 *
 */

@Entity
@Table(name="UserRole")
public class UserRole {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String name;

	public UserRole() {
	}

	public String getName() {
		return name;
	}

	public Integer getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Role: " + name;
	}
}
