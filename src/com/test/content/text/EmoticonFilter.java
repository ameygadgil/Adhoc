/**
 * 
 */
package com.test.content.text;

import java.util.regex.Pattern;

import org.json.JSONArray;

/**
 * @author Amey Gadgil
 * Implementation to filter emoticon texts and create JSON array of the found elements.
 *
 */
class EmoticonFilter extends TextContentFilter {

	Pattern mEmoticonPattern;
	
	public EmoticonFilter(String filterName) {
		super(filterName);
		/*
		 * Create regex to match requirements of an emoticon mentioned in
		 * problem statement 1. Alphanumeric String (alphabets and numbers) 2.
		 * contained in parenthesis 3. at least 1 but not more than 15 chars in
		 * length.
		 */
		mEmoticonPattern = Pattern.compile("^\\([a-zA-Z0-9_]{1,15}\\)$",
				Pattern.CASE_INSENSITIVE);
	}

	@Override
	public void evaluateToken(String token, JSONArray output) {

		if (mEmoticonPattern.matcher(token).matches()) {
			/*
			 * lookup for emoticons in available set, for problem statement it's
			 * not required. Anything that matches regex conditions can be
			 * assumed as an emoticon.
			 */

			// Add to JSON
			if (null != output) {
				// exclude ( and ) from token
				output.put(token.substring(1, token.length() - 1));
			}
		}
	}
}
