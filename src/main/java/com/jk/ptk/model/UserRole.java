package com.jk.ptk.model;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TypedQuery;

import com.jk.ptk.app.DataLayerInitialized;

/**
 * Represents user role.
 * 
 * @author Jitendra
 *
 */

@Entity
@DataLayerInitialized
public class UserRole {
	/**
	 * Default user role to use when creating a new user.
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
	private String name;

	public UserRole() {
	}

	public String getName() {
		return name;
	}

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
	public String toString() {
		return "UserRole [id=" + id + ", name=" + name + "]";
	}

}
