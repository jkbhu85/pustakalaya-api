package com.jk.ptk.f.user;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TypedQuery;

import com.jk.ptk.app.DataLayerInitialized;

/**
 * Represents user account status.
 * @author Jitendra
 *
 */

@Entity
@Table(name = "UserAccountStatus")
@DataLayerInitialized
public class UserAcStatus {
	public static final UserAcStatus ACTIVE = new UserAcStatus();
	public static final UserAcStatus LOCKED = new UserAcStatus();
	public static final UserAcStatus CLOSED = new UserAcStatus();
	public static final UserAcStatus REVOKED = new UserAcStatus();
	public static final UserAcStatus INCOMPLETE = new UserAcStatus();

	private static final UserAcStatus[] acStatusArr = { ACTIVE, LOCKED, CLOSED, REVOKED, INCOMPLETE };

	/**
	 * Returns an object representing user account status for the specified
	 * {@code statusId}. If no status matching {@code statusId} exists then returns
	 * {@code null}.
	 * 
	 * @param statusId
	 *            the specified user account status id
	 * @return an object representing user account status for the specified
	 *         {@code statusId} or returns {@code null} if no status matching
	 *         {@code statusId} exists
	 */
	public static UserAcStatus fromId(Integer statusId) {
		for (UserAcStatus s : acStatusArr) {
			if (s.getId() == statusId) return s;
		}

		return null;
	}

	public static void init(EntityManager em) {
		final String hql = "select s from UserAcStatus s where s.name=:name";
		TypedQuery<UserAcStatus> query;
		UserAcStatus acStatus;

		query = em.createQuery(hql, UserAcStatus.class);
		query.setParameter("name", "ACTIVE");
		acStatus = query.getSingleResult();
		ACTIVE.id = acStatus.id;
		ACTIVE.name = acStatus.name;

		query = em.createQuery(hql, UserAcStatus.class);
		query.setParameter("name", "LOCKED");
		acStatus = query.getSingleResult();
		LOCKED.id = acStatus.id;
		LOCKED.name = acStatus.name;

		query = em.createQuery(hql, UserAcStatus.class);
		query.setParameter("name", "CLOSED");
		acStatus = query.getSingleResult();
		CLOSED.id = acStatus.id;
		CLOSED.name = acStatus.name;

		query = em.createQuery(hql, UserAcStatus.class);
		query.setParameter("name", "REVOKED");
		acStatus = query.getSingleResult();
		REVOKED.id = acStatus.id;
		REVOKED.name = acStatus.name;

		query = em.createQuery(hql, UserAcStatus.class);
		query.setParameter("name", "INCOMPLETE");
		acStatus = query.getSingleResult();
		INCOMPLETE.id = acStatus.id;
		INCOMPLETE.name = acStatus.name;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private String name;

	public UserAcStatus() {
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		UserAcStatus other = (UserAcStatus) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		if (name == null) {
			if (other.name != null) return false;
		} else if (!name.equals(other.name)) return false;
		return true;
	}

	@Override
	public String toString() {
		return "UserAcStatus [id=" + id + ", name=" + name + "]";
	}

}
