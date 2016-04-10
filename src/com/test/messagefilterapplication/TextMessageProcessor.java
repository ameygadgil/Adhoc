/**
 * 
 */
package com.test.messagefilterapplication;

import java.util.ArrayList;

import android.content.Context;

import com.example.messagefilterapplication.R;
import com.test.content.ContentHandlerFactory;
import com.test.content.ContentHandlerFactory.ContentHandlerType;
import com.test.content.IContentHandler;

/**
 * @author Amey Gadgil
 *
 */
public class TextMessageProcessor {
	private static TextMessageProcessor mInstance;
	
	private IContentHandler<String, String> mTextContentHandler;
	private Context mContext;
	
	private ArrayList<ListStateHolder> mAvailableFilterList;
	private TextMessageProcessor(Context context){
		mTextContentHandler = (IContentHandler<String, String>) ContentHandlerFactory.create(ContentHandlerType.Text);
		setContext(context);
	}
	
	public static final TextMessageProcessor getInstance(Context context){
		if(null == mInstance){
			mInstance = new TextMessageProcessor(context.getApplicationContext());
		}
		if(null == mInstance.mContext){
			mInstance.setContext(context.getApplicationContext());
		}
		
		return mInstance;
	}
	
	public String getContent(String input){
		return mTextContentHandler.getContent(input);
	}
	
	private void setContext(Context context) {
		if (context != null) {
			mContext = context;
			String[] filterListItems = mContext.getResources().getStringArray(
					R.array.filter_list);
			mAvailableFilterList = new ArrayList<ListStateHolder>();
			if (filterListItems != null && filterListItems.length > 0) {
				for (int i = 0; i < filterListItems.length; i++) {
					mAvailableFilterList.add(new ListStateHolder(
							filterListItems[i], true));
				}
			}
			updateFilters();
		}
	}
	
	/*
	public boolean addFilter(String filterName){
		return mTextContentHandler.addFilter(filterName);
	}
	
	public boolean removeFilter(String filterName){
		return mTextContentHandler.addFilter(filterName);
	}*/
	
	class ListStateHolder {
		String mLabel;
		boolean isChecked;

		public ListStateHolder(String label, boolean isChecked) {
			this.isChecked = isChecked;
			mLabel = label;
		}
	}
	
	public ArrayList<ListStateHolder> getAvailableFilterList(){
		/* Should send out a clone here. The actual object should not be exposed.
		 * For the glue code to test functionality sending actual object
		 */
		return (ArrayList<ListStateHolder>) mAvailableFilterList;
	}
	
	/*
	 * Adding and removing filters every time without validations is Not the
	 * best of the logics, but for assignment testing, it's kept like that.
	 */
	public void updateFilters(){
		for(ListStateHolder holder: mAvailableFilterList){
			if(holder.isChecked){
				mTextContentHandler.addFilter(holder.mLabel);
			}else{
				mTextContentHandler.removeFilter(holder.mLabel);
			}
		}
		
	}

}
