/**
 * 
 */
package com.test.content;

/**
 * 
 * @author Amey Gadgil
 *
 * @param <T> - Type of output passed to this listener.
 */
public interface IFilterCompleteListener<T> {
	public void onFilterComplete(String filterName, T filterOutput);
}
