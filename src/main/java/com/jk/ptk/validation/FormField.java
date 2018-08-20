package com.jk.ptk.validation;

import java.util.List;

/**
 * Represents a form field.
 *
 * @author Jitendra
 *
 */
public class FormField {
	private String value;
	private String fieldName;
	private boolean mandatory;
	private List<FormField> crossFieldList;

	/**
	 * Default constructor.
	 */
	public FormField() {
	}

	/**
	 * Creates an instance with specified value, field name and mandatory status.
	 *
	 * @param value
	 *            the specified value
	 * @param fieldName
	 *            the specified field
	 * @param mandatory
	 *            whether field is mandatory
	 */
	public FormField(String value, String fieldName, boolean mandatory) {
		this.value = value;
		this.fieldName = fieldName;
		this.mandatory = mandatory;
	}

	/**
	 * Creates an instance with specified value, field name, mandatory status and
	 * list of cross fields to use for validation if any.
	 *
	 * @param value
	 * @param fieldName
	 * @param mandatory
	 * @param crossFieldList
	 */
	public FormField(String value, String fieldName, boolean mandatory, List<FormField> crossFieldList) {
		this.value = value;
		this.fieldName = fieldName;
		this.mandatory = mandatory;
		this.crossFieldList = crossFieldList;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 *            the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}

	/**
	 * @return the fieldName
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * @param fieldName
	 *            the fieldName to set
	 */
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	/**
	 * @return the mandatory
	 */
	public boolean isMandatory() {
		return mandatory;
	}

	/**
	 * @param mandatory
	 *            the mandatory to set
	 */
	public void setMandatory(boolean mandatory) {
		this.mandatory = mandatory;
	}

	/**
	 * @return the crossFieldList
	 */
	public List<FormField> getCrossFieldList() {
		return crossFieldList;
	}

	/**
	 * @param crossFieldList
	 *            the crossFieldList to set
	 */
	public void setCrossFieldList(List<FormField> crossFieldList) {
		this.crossFieldList = crossFieldList;
	}

}
