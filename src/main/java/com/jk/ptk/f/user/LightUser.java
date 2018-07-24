package com.jk.ptk.f.user;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "LibUser")
public class LightUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String email;

	private String firstName;

	private String lastName;

	public LightUser() {
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	/**
	 * Returns an instance of LightUser created from the specified user or
	 * {@code null} if {@code user} is {@code null}.
	 *
	 * @param user
	 *            the specified user
	 * @return an instance of LightUser created from the specified user or
	 *         {@code null} if {@code user} is {@code null}
	 */
	public static LightUser fromUser(User user) {
		if (user == null)
			return null;

		LightUser lu = new LightUser();

		lu.id = user.getId();
		lu.email = user.getEmail();
		lu.firstName = user.getFirstName();
		lu.lastName = user.getLastName();

		return lu;
	}
}
