/**
 * 
 */
package com.test.content.text;

import java.io.IOException;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author Amey Gadgil
 *
 */
class LINKFilter extends TextContentFilter {
	Pattern mLinkPattern;

	public LINKFilter(String filterName) {
		super(filterName);
		mLinkPattern = Pattern.compile("^(f|ht)tps?://.*$",
				Pattern.CASE_INSENSITIVE);
	}

	@Override
	public void evaluateToken(String token, JSONArray output) {
		if (mLinkPattern.matcher(token).matches()) {
			// add json here
			if (null != output) {
				try{
				JSONObject linkObj = new JSONObject();
				linkObj.put("url", token);
				String title = "";
				try {
					title = WebPageTitleUtil.getPageTitle(token);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if(null == title || title.trim().length() <=0){
					title = "could not get title";
				}
				linkObj.put("title", title );

				output.put(linkObj);
				}catch(JSONException e){
					
				}
			}
		}
	}

}
