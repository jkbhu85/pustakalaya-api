package com.jk.ptk.f.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name = "LibUser")
@NamedQuery(name="find_lightUser_by_email", query="select u from LightUser u where u.email=:email")
public class LightUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "emailUk", nullable = false, unique = true)
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
	 * @param user the specified user
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
