/**
 * 
 */
package com.test.content.text;

/**
 * @author Amey Gadgil
 * 
 * Factory class to instantiate available Text filters. Available filter types listed in enum.
 *
 */
public class TextContentFilterFactory {
	public static final String TEXT_FILTER_TYPE_LINK = "links";
	public static final String TEXT_FILTER_TYPE_EMOTICON = "emoticons";
	public static final String TEXT_FILTER_TYPE_MENTION = "mentions";
	
	/*public static enum Filter{
		LINK("links"),
		EMOTICON("emoticons"),
		MENTION("mentions");
		
		String name;
		Filter(String filterName){
			name = filterName;
		}
		
		public String getName() {
			return name;
		}
		
	}*/
	
	private TextContentFilterFactory(){
		
	}
	
	/**
	 * Creates and returns content filter of type mentioned in FilterFactory.Filter
	 * @param filterType - String Constants exposed in TextContentFilterFactory.
	 * @return IContentFilter object
	 */
	public static ITextContentFilter createFilter(String filterType) {
		filterType = filterType.trim().toLowerCase();
		switch (filterType) {
			case TEXT_FILTER_TYPE_LINK:
				return new LINKFilter(filterType);
			case TEXT_FILTER_TYPE_MENTION:
				return new MentionFilter(filterType);
			case TEXT_FILTER_TYPE_EMOTICON:
				return new EmoticonFilter(filterType);
			default:
				return null;
		}
	}
	/*public static ITextContentFilter createFilter(Filter filterType) {
		switch (filterType) {
			case LINK:
				return new LINKFilter(filter.getName());
			case MENTION:
				return new MentionFilter(filter.getName());
			case EMOTICON:
				return new EmoticonFilter(filter.getName());
			default:
				return null;
		}
	}*/

}
