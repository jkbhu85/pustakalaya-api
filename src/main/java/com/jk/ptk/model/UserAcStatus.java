package com.jk.ptk.model;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TypedQuery;

import com.jk.ptk.app.DataLayerInitialized;

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
		ACTIVE.setId(acStatus.getId());
		ACTIVE.setName(acStatus.getName());

		query = em.createQuery(hql, UserAcStatus.class);
		query.setParameter("name", "LOCKED");
		acStatus = query.getSingleResult();
		LOCKED.setId(acStatus.getId());
		LOCKED.setName(acStatus.getName());

		query = em.createQuery(hql, UserAcStatus.class);
		query.setParameter("name", "CLOSED");
		acStatus = query.getSingleResult();
		CLOSED.setId(acStatus.getId());
		CLOSED.setName(acStatus.getName());

		query = em.createQuery(hql, UserAcStatus.class);
		query.setParameter("name", "REVOKED");
		acStatus = query.getSingleResult();
		REVOKED.setId(acStatus.getId());
		REVOKED.setName(acStatus.getName());

		query = em.createQuery(hql, UserAcStatus.class);
		query.setParameter("name", "INCOMPLETE");
		acStatus = query.getSingleResult();
		INCOMPLETE.setId(acStatus.getId());
		INCOMPLETE.setName(acStatus.getName());
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

	private void setId(Integer id) {
		this.id = id;
	}

	private void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "UserAcStatus [id=" + id + ", name=" + name + "]";
	}

}
