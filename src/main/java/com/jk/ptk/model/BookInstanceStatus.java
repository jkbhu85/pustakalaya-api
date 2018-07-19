package com.jk.ptk.model;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TypedQuery;

import com.jk.ptk.app.DataLayerInitialized;

@Entity
@DataLayerInitialized
public class BookInstanceStatus {
	public static final BookInstanceStatus DEFAULT_BOOK_INSTANCE_STATUS = new BookInstanceStatus();
	
	public static final BookInstanceStatus ISSUED = new BookInstanceStatus();
	public static final BookInstanceStatus AVAILABLE = new BookInstanceStatus();
	public static final BookInstanceStatus UNAVAILABLE = new BookInstanceStatus();
	public static final BookInstanceStatus REMOVED = new BookInstanceStatus();
	
	private static final BookInstanceStatus[] biStatusArr = {
			ISSUED,
			AVAILABLE,
			UNAVAILABLE,
			REMOVED
	};
	
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
	public static BookInstanceStatus fromId(Integer statusId) {
		for (BookInstanceStatus s : biStatusArr) {
			if (s.getId() == statusId) return s;
		}

		return null;
	}

	public static void init(EntityManager em) {
		final String hql = "select s from BookInstanceStatus s where s.name=:name";
		TypedQuery<BookInstanceStatus> query;
		BookInstanceStatus biStatus;
		
		query = em.createQuery(hql, BookInstanceStatus.class);
		query.setParameter("name", "ISSUED");
		biStatus = query.getSingleResult();
		ISSUED.setId(biStatus.getId());
		ISSUED.setName(biStatus.getName());

		query = em.createQuery(hql, BookInstanceStatus.class);
		query.setParameter("name", "AVAILABLE");
		biStatus = query.getSingleResult();
		AVAILABLE.setId(biStatus.getId());
		AVAILABLE.setName(biStatus.getName());
		
		DEFAULT_BOOK_INSTANCE_STATUS.setId(AVAILABLE.getId());
		DEFAULT_BOOK_INSTANCE_STATUS.setName(AVAILABLE.getName());
		
		query = em.createQuery(hql, BookInstanceStatus.class);
		query.setParameter("name", "UNAVAILABLE");
		biStatus = query.getSingleResult();
		UNAVAILABLE.setId(biStatus.getId());
		UNAVAILABLE.setName(biStatus.getName());
		
		query = em.createQuery(hql, BookInstanceStatus.class);
		query.setParameter("name", "REMOVED");
		biStatus = query.getSingleResult();
		REMOVED.setId(biStatus.getId());
		REMOVED.setName(biStatus.getName());
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	private String name;
	
	private BookInstanceStatus() {
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
	
}