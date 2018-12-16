package com.jk.ptk.mail;

import java.time.LocalDate;

public class BookAssignedMail {
	private String bookTitle;
	private String firstName;
	private String lastName;
	private String email;
	private String locale;
	private LocalDate assignedOn;
	private LocalDate returnOn;

	public BookAssignedMail() {}

	public String getBookTitle() {
		return bookTitle;
	}

	public void setBookTitle(String bookTitle) {
		this.bookTitle = bookTitle;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public LocalDate getAssignedOn() {
		return assignedOn;
	}

	public void setAssignedOn(LocalDate assignedOn) {
		this.assignedOn = assignedOn;
	}

	public LocalDate getReturnOn() {
		return returnOn;
	}

	public void setReturnOn(LocalDate returnOn) {
		this.returnOn = returnOn;
	}

}
