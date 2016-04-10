/**
 * 
 */
package com.test.content.text;

import org.json.JSONArray;
import org.json.JSONObject;

import com.test.content.IFilterCompleteListener;

/**
 * @author Amey Gadgil 
 *
 */
abstract class TextContentFilter implements ITextContentFilter {
	
	private IFilterCompleteListener<Object> mFilterCompleteListener;
	protected String mFiltername;
	protected String mInputText = "";
	protected String mOutputText = "";
	protected JSONObject outputJSON;
	
	public TextContentFilter(String name){
		mFiltername = name;
	}

	public final void filterContent(String input, IFilterCompleteListener<Object> listener) {
		//validate input
		if(input == null || input.trim().length() == 0){
			onFilterComplete(mFiltername, null);
			return;
		}
		
		mInputText = input;
		mFilterCompleteListener = listener;
		mOutputText = "";
		outputJSON = null;
		
		//A thread pool utility can be used here.
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				JSONArray output = new JSONArray();
				String[] tokens = mInputText.split(" ");
				for(int i=0; i<tokens.length;i++){
					evaluateToken(tokens[i], output);
				}
				
				onFilterComplete(mFiltername, (output.length() > 0)?output:null);
			}
		}).start();
	}

	public final void onFilterComplete(String name, Object output) {
		if(null != mFilterCompleteListener){
			mFilterCompleteListener.onFilterComplete(name, output);
		}
	}
	
	public String getFilterName(){
		return mFiltername;
	}
	
	public abstract void evaluateToken(String token, JSONArray output);

}
