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
	/**
	 * Default user role to take when creating a new user.
	 * 
	 * <p>The current default role is MEMBER.</p>
	 */
	public static final int DEFAULT_USER_ROLE = 3;
	
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
	
	public void setId(int id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "Role: " + name;
	}
}
