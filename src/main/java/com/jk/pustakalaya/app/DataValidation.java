package com.jk.pustakalaya.app;


public interface DataValidation<T> {
	void validate(T obj) throws ValidationFailedException;
}
