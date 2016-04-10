/**
 * 
 */
package com.test.content;

/**
 * 
 * @author Amey Gadgil
 * Interface to get filtered content in form of Q for input in form of T
 * @param <T> - Input type
 * @param <Q> - Output Type
 */
public interface IContentHandler<T,Q> {
	public Q getContent(T input);
	public boolean addFilter(String filterName);
	public boolean removeFilter(String filterName);
}
