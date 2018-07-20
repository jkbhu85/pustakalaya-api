package com.jk.ptk.f.book;

import javax.persistence.Entity;
import javax.persistence.EntityManager;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TypedQuery;

import com.jk.ptk.app.DataLayerInitialized;

/**
 * Represents book category.
 * @author Jitendra
 *
 */

@Entity
@DataLayerInitialized
public class BookCategory {

	public static final BookCategory TEXT_BOOK = new BookCategory();
	public static final BookCategory MAGAZINE = new BookCategory();

	public static final BookCategory[] categories = { TEXT_BOOK, MAGAZINE };

	/**
	 * Returns an object representing book category for the specified
	 * {@code categoryId}. If no category matching {@code categoryId} exists then
	 * returns {@code null}.
	 * 
	 * @param categoryId
	 *            the specified book category id
	 * @return an object representing book category for the specified
	 *         {@code categoryId} or returns {@code null} if no category matching
	 *         {@code categoryId} exists
	 */
	public static BookCategory fromId(Integer categoryId) {
		for (BookCategory c : categories) {
			if (c.getId() == categoryId) return c;
		}

		return null;
	}

	public static void init(EntityManager em) {
		final String jpql = "select c from BookCategory c where name=:name";
		BookCategory cat;
		TypedQuery<BookCategory> query;

		query = em.createQuery(jpql, BookCategory.class);
		query.setParameter("name", "TEXTBOOK");
		cat = query.getSingleResult();
		TEXT_BOOK.id = cat.id;
		TEXT_BOOK.name = cat.name;

		query = em.createQuery(jpql, BookCategory.class);
		query.setParameter("name", "MAGAZINE");
		cat = query.getSingleResult();
		MAGAZINE.id = cat.id;
		MAGAZINE.name = cat.name;
	}

	public BookCategory() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

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

		BookCategory other = (BookCategory) obj;

		if (id == null) {
			if (other.id != null) return false;
		} else if (!id.equals(other.id)) return false;
		if (name == null) {
			if (other.name != null) return false;
		} else if (!name.equals(other.name)) return false;

		return true;
	}

}
