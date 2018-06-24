package com.jk.ptk.app;


public interface DataValidation<T> {
	void validate(T obj) throws ValidationFailedException;
}
