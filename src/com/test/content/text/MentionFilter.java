/**
 * 
 */
package com.test.content.text;

import java.util.regex.Pattern;

import org.json.JSONArray;

/**
 * @author Amey Gadgil
 *
 */
class MentionFilter extends TextContentFilter {
	Pattern mMentionPattern;

	public MentionFilter(String filterName) {
		super(filterName);
		mMentionPattern = Pattern.compile("^@.+", Pattern.CASE_INSENSITIVE);
	}

	@Override
	public void evaluateToken(String token, JSONArray output) {
		if (mMentionPattern.matcher(token).matches()) {
			// add json here
			if (null != output) {
				// exclude preceding @ char.
				output.put(token.substring(1, token.length()));
			}
		}
	}

}
