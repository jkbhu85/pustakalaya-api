package com.jk.ptk.f.bookinstance;

import com.jk.ptk.f.book.Book;

public class BookInstanceUtil {
	public static BookInstanceValue toValue(BookInstance bi) {
		BookInstanceValue v = new BookInstanceValue();
		Book book = bi.getBook();
		
		v.setId(Long.toString(bi.getId()));
		v.setTitle(book.getTitle());
		v.setAuthors(book.getAuthors());

		v.setNumOfPages(Integer.toString(bi.getNumOfPages()));
		
		if (book.getEdition() != null)
		v.setEdition(Integer.toString(book.getEdition()));
		
		v.setIsbn(book.getIsbn());
		v.setPublication(bi.getPublication());
		v.setVolume(Integer.toString(bi.getVolume()));
		
		v.setBookCategory(book.getBookCategory().getName());
		v.setStatus(bi.getStatus().getName());
		
		if (bi.getPrice() != null)
		v.setPrice(bi.getPrice().toString());
		v.setCurrency(bi.getCurrency().getCode());
		
		v.setAddedOn(bi.getAddedOn().toString());
		v.setComments(bi.getComments());
		
		return v;
	}
}
