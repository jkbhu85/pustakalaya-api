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
 * Represents status of a book instance.
 *
 * @author Jitendra
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

	public BookInstanceStatus() {}

	/**
	 * Creates a book instance with status name as the specified {@code name} and
	 * without id.
	 * 
	 * @param name
	 *             the specified status name.
	 */
	public BookInstanceStatus(String name) {
		this.name = name;
	}

	/**
	 * Returns an object representing book instance status for the specified
	 * {@code statusId}. If no status matching {@code statusId} exists then returns
	 * {@code null}.
	 *
	 * @param statusId
	 *                 the specified book instance status
	 * @return an object representing book instance status for the specified
	 *         {@code statusId} or returns {@code null} if no status matching
	 *         {@code statusId} exists
	 */
	public static BookInstanceStatus fromId(Integer statusId) {
		for (BookInstanceStatus s : biStatusArr) {
			if (s.getId() == statusId)
				return s;
		}

		return null;
	}

	/**
	 * Initializes the constants defined in this class from by fetching values from
	 * the storage system.
	 * 
	 * @param em
	 *           the specified entity manager.
	 */
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
		BookInstanceStatus other = (BookInstanceStatus) obj;
		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		if (name == null) {
			if (other.name != null) return false;
		} else if (!name.equals(other.name)) return false;
		return true;
	}

}
