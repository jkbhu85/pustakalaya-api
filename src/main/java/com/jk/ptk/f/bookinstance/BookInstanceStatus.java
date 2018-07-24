package com.jk.ptk.f.bookinstance;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TypedQuery;

import com.jk.ptk.app.DataLayerInitialized;

/**
 * Represents a book instance status.
 *
 * @author Jitendra
 *
 */

@Entity
@DataLayerInitialized
public class BookInstanceStatus {
	public static final BookInstanceStatus DEFAULT_BOOK_INSTANCE_STATUS = new BookInstanceStatus();

	public static final BookInstanceStatus ISSUED = new BookInstanceStatus();
	public static final BookInstanceStatus AVAILABLE = new BookInstanceStatus();
	public static final BookInstanceStatus UNAVAILABLE = new BookInstanceStatus();
	public static final BookInstanceStatus REMOVED = new BookInstanceStatus();

	private static final BookInstanceStatus[] biStatusArr = { ISSUED, AVAILABLE, UNAVAILABLE, REMOVED };

	/**
	 * Returns an object representing book instance status for the specified
	 * {@code statusId}. If no status matching {@code statusId} exists then returns
	 * {@code null}.
	 *
	 * @param statusId
	 *            the specified book instance status
	 * @return an object representing book instance status for the specified
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
		ISSUED.id = biStatus.id;
		ISSUED.name = biStatus.name;

		query = em.createQuery(hql, BookInstanceStatus.class);
		query.setParameter("name", "AVAILABLE");
		biStatus = query.getSingleResult();
		AVAILABLE.id = biStatus.id;
		AVAILABLE.name = biStatus.name;

		DEFAULT_BOOK_INSTANCE_STATUS.id = AVAILABLE.id;
		DEFAULT_BOOK_INSTANCE_STATUS.name = AVAILABLE.name;

		query = em.createQuery(hql, BookInstanceStatus.class);
		query.setParameter("name", "UNAVAILABLE");
		biStatus = query.getSingleResult();
		UNAVAILABLE.id = biStatus.id;
		UNAVAILABLE.name = biStatus.name;

		query = em.createQuery(hql, BookInstanceStatus.class);
		query.setParameter("name", "REMOVED");
		biStatus = query.getSingleResult();
		REMOVED.id = biStatus.id;
		REMOVED.name = biStatus.name;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false)
	private String name;

	private BookInstanceStatus() {
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}
}