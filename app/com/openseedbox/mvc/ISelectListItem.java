package com.openseedbox.mvc;

/**
 * This interface represents an <option> element in a HTML <select> tag
 * It exists so classes can implement it and easily provide a dropdown
 * list for the #{form.dropdown} tag
 * @author Erin Drummond
 */
public interface ISelectListItem {
	
	/**
	 * @return The text the user sees for this item
	 */
	public String getName();
	
	/**
	 * @return The value that gets sent back to the server if the item
	 * is selected by the user
	 */
	public String getValue();
	
	/**
	 * @return Whether or not this item should have the selected='selected'
	 * attribute set
	 */
	public boolean isSelected();
	
}
