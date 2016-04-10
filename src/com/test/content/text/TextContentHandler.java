/**
 * 
 */
package com.test.content.text;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

import org.json.JSONException;
import org.json.JSONObject;

import com.test.content.IContentHandler;
import com.test.content.IFilterCompleteListener;

/**
 * @author Amey Gadgil
 * Content handler that works with String input and returns String output  as JSON string.
 *
 */
public class TextContentHandler implements IContentHandler<String, String> {

	private ArrayList<ITextContentFilter> mContentFilters;
	private JSONObject mOutput;

	private IFilterCompleteListener<Object> mFilterCompleteListener = new IFilterCompleteListener<Object>() {

		@Override
		public void onFilterComplete(String filterName, Object filterOutput) {

			if (mOutput == null) {
				mOutput = new JSONObject();
			}
			if (filterOutput != null) {
				try{
					mOutput = mOutput.putOpt(filterName, filterOutput);
				}catch(JSONException e){
					
				}
			}
			mHandlersFinished.countDown();

		}
	};

	private CountDownLatch mHandlersFinished;

	public TextContentHandler() {
		setup();
	}

	// Add all filters for now. This can be separated and kept in config file.
	private void setup() {
		if (null == mContentFilters) {
			mContentFilters = new ArrayList<ITextContentFilter>();
		}
		/*mContentFilters.add(TextContentFilterFactory
				.createFilter(Filter.EMOTICON));
		mContentFilters.add(TextContentFilterFactory
				.createFilter(Filter.MENTION));
		mContentFilters.add(TextContentFilterFactory.createFilter(Filter.LINK));*/
	}

	@Override
	public synchronized String getContent(String input) {
		mOutput = null;
		mHandlersFinished = new CountDownLatch(mContentFilters.size());
		for (ITextContentFilter filter : mContentFilters) {
			filter.filterContent(input, mFilterCompleteListener);
		}
		try {
			mHandlersFinished.await();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(mOutput.length() <= 0)
			return "";
		return mOutput.toString();
	}

	@Override
	public boolean addFilter(String filterName) {
		if(null == filterName || filterName.length() <= 0 ){
			return false;
		}
		
		//check if already added
		for (ITextContentFilter filter : mContentFilters) {
			if (filter.getFilterName().equals(filterName)) {
				return true;
			}
		}
			
		ITextContentFilter filter = TextContentFilterFactory.createFilter(filterName);
		if(null != filter){
			if (null == mContentFilters) {
				mContentFilters = new ArrayList<ITextContentFilter>();
			}
			synchronized (mContentFilters) {
				mContentFilters.add(filter);
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean removeFilter(String filterName) {
		if(null == filterName || filterName.length() <= 0 || null==mContentFilters || mContentFilters.size() <= 0){
			return false;
		}
		
		filterName = filterName.trim().toLowerCase();
		
		for(ITextContentFilter filter: mContentFilters){
			if(filter.getFilterName().equals(filterName)){
				synchronized (mContentFilters) {
					mContentFilters.remove(filter);
				}
				
				return true;
			}
		}
		return false;
	}
}
