package com.jk.ptk.f.bookinstance;

public class BookInstanceV {
	public static final String FIELD_BOOK_ID = "bookId";
	public static final String FIELD_PRICE = "price";
	public static final String FIELD_CURRENCY = "currency";
	public static final String FIELD_NUMBER_OF_PAGES = "numberOfPages";
	public static final String FIELD_PUBLICATION = "publication";
	public static final String FIELD_VOLUME = "volume";
	public static final String FIELD_NO_OF_COPIES = "noOfCopies";

	private String bookId;
	private String price;
	private String currency;
	private String numberOfPages;
	private String publication;
	private String volume;
	private String noOfCopies;
	

	/**
	 * @return the bookId
	 */
	public String getBookId() {
		return bookId;
	}

	/**
	 * @param bookId
	 *                 the bookId to set
	 */
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}

	/**
	 * @return the price
	 */
	public String getPrice() {
		return price;
	}

	/**
	 * @param price
	 *              the price to set
	 */
	public void setPrice(String price) {
		this.price = price;
	}

	/**
	 * @return the currency
	 */
	public String getCurrency() {
		return currency;
	}

	/**
	 * @param currency
	 *                 the currency to set
	 */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/**
	 * @return the numberOfPages
	 */
	public String getNumberOfPages() {
		return numberOfPages;
	}

	/**
	 * @param numberOfPages
	 *                      the numberOfPages to set
	 */
	public void setNumberOfPages(String numberOfPages) {
		this.numberOfPages = numberOfPages;
	}

	/**
	 * @return the publication
	 */
	public String getPublication() {
		return publication;
	}

	/**
	 * @param publication
	 *                    the publication to set
	 */
	public void setPublication(String publication) {
		this.publication = publication;
	}

	/**
	 * @return the volume
	 */
	public String getVolume() {
		return volume;
	}

	/**
	 * @param volume
	 *               the volume to set
	 */
	public void setVolume(String volume) {
		this.volume = volume;
	}

	/**
	 * @return the noOfCopies
	 */
	public String getNoOfCopies() {
		return noOfCopies;
	}

	/**
	 * @param noOfCopies
	 *                   the noOfCopies to set
	 */
	public void setNoOfCopies(String noOfCopies) {
		this.noOfCopies = noOfCopies;
	}

}
