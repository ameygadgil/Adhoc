
package com.test.content;

/**
 * 
 * @author Amey Gadgil
 * 
 * Interface to pass content in T
 * form and get filtered output in Q form. Q depends on underlying
 * Filter implementations.
 * 
 * @param <T>
 *            - Type of Input object
 * @param <Q>
 *            - Type of output object
 */
public interface IContentFilter<T, Q> {
	public void filterContent(T input, IFilterCompleteListener<Q> listener);
	public String getFilterName();
}
