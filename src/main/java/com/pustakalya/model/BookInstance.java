package com.pustakalya.model;

import java.util.Date;

/**
 * Represents instance of book.
 * 
 * @author Jitendra
 *
 */
public class BookInstance {
	private int instanceId;
	private String status;
	private int addedBy;
	private int removedBy;
	private Date addedOn;
	private Date removedOn;
	private String reasonOfRemoval;
	private Book book;
	
	
	public BookInstance() {
		this(0);
	}
	
	public BookInstance(int instanceId) {
		this.instanceId = instanceId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getAddedBy() {
		return addedBy;
	}

	public void setAddedBy(int addedBy) {
		this.addedBy = addedBy;
	}

	public int getRemovedBy() {
		return removedBy;
	}

	public void setRemovedBy(int removedBy) {
		this.removedBy = removedBy;
	}

	public Date getAddedOn() {
		return addedOn;
	}

	public void setAddedOn(Date addedOn) {
		this.addedOn = addedOn;
	}

	public Date getRemovedOn() {
		return removedOn;
	}

	public void setRemovedOn(Date removedOn) {
		this.removedOn = removedOn;
	}

	public String getReasonOfRemoval() {
		return reasonOfRemoval;
	}

	public void setReasonOfRemoval(String reasonOfRemoval) {
		this.reasonOfRemoval = reasonOfRemoval;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	public int getInstanceId() {
		return instanceId;
	}
	
}
