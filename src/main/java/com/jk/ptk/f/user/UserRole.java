package com.jk.ptk.f.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TypedQuery;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.jk.ptk.app.DataLayerInitialized;

/**
 * Represents a user role.
 *
 * @author Jitendra
 *
 */

@Entity
@DataLayerInitialized
public class UserRole {
	/**
	 * Default user role to be used when creating a new user.
	 *
	 * <p>
	 * The current default role is MEMBER.
	 * </p>
	 */
	public static final UserRole DEFAULT_USER_ROLE = new UserRole();

	public static final UserRole ADMIN = new UserRole();
	public static final UserRole LIBRARIAN = new UserRole();
	public static final UserRole MEMBER = new UserRole();
	public static final UserRole NONE = new UserRole();

	public static final UserRole[] roles = { ADMIN, LIBRARIAN, MEMBER, NONE };

	/**
	 * Returns an object representing user role for the specified {@code roleName}. If
	 * no role matching {@code roleName} exists then returns {@code null}.
	 *
	 * @param roleName
	 *            the specified user role name
	 * @return an object representing user role for the specified {@code roleId} or
	 *         returns {@code null} if no status matching {@code statusId} exists
	 */
	@JsonCreator
	public static UserRole from(String roleName) {
		for (UserRole r : roles) {
			if (r.getName().equals(roleName))
				return r;
		}

		return null;
	}

	public static void init(EntityManager em) {
		final String hql = "select r from UserRole r where r.name=:name";
		TypedQuery<UserRole> query;
		UserRole role;

		query = em.createQuery(hql, UserRole.class);
		query.setParameter("name", "ADMIN");
		role = query.getSingleResult();
		ADMIN.setId(role.getId());
		ADMIN.setName(role.getName());

		query = em.createQuery(hql, UserRole.class);
		query.setParameter("name", "LIBRARIAN");
		role = query.getSingleResult();
		LIBRARIAN.setId(role.getId());
		LIBRARIAN.setName(role.getName());

		query = em.createQuery(hql, UserRole.class);
		query.setParameter("name", "MEMBER");
		role = query.getSingleResult();
		MEMBER.setId(role.getId());
		MEMBER.setName(role.getName());

		// set default user role
		DEFAULT_USER_ROLE.setId(MEMBER.getId());
		DEFAULT_USER_ROLE.setName(MEMBER.getName());

		query = em.createQuery(hql, UserRole.class);
		query.setParameter("name", "NONE");
		role = query.getSingleResult();
		NONE.setId(role.getId());
		NONE.setName(role.getName());
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false)
	private String name;

	public UserRole() {
	}

	public String getName() {
		return name;
	}

	@JsonValue
	public Integer getId() {
		return id;
	}

	private void setId(Integer id) {
		this.id = id;
	}

	private void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		// return sum of hashcodes of id and name
		return ((id == null) ? 0 : id.hashCode()) + ((name == null) ? 0 : name.hashCode());
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == this)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;

		UserRole other = (UserRole) obj;

		if (id == null || name == null)
			return false;

		return (id.equals(other.id) && name.equals(other.name));
	}

	@Override
	public String toString() {
		return "UserRole [id=" + id + ", name=" + name + "]";
	}

}
