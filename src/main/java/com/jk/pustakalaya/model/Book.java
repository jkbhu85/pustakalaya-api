package com.jk.pustakalaya.model;

/**
 * Represents book in database.
 * 
 * @author Jitendra
 *
 */
public class Book {
	private int bookId;
	private String title;
	private String author;
	private int editon;
	private double price;
	private int noOfPages;
	private String isbn;
	private String publication;
	
	public Book() {
		this(0);
	}
	
	public Book(int bookId) {
		this.bookId = bookId;
	}

	public int getBookId() {
		return bookId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public int getEditon() {
		return editon;
	}

	public void setEditon(int editon) {
		this.editon = editon;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getNoOfPages() {
		return noOfPages;
	}

	public void setNoOfPages(int noOfPages) {
		this.noOfPages = noOfPages;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getPublication() {
		return publication;
	}

	public void setPublication(String publication) {
		this.publication = publication;
	}
}
