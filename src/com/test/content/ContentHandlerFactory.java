/**
 * 
 */
package com.test.content;

import com.test.content.text.TextContentHandler;

/**
 * 
 * @author Amey Gadgil
 *
 *         Class to instantiate and use ContentHandlers. The available types of
 *         content handlers are listed in the enum.
 */
public class ContentHandlerFactory {
	
	/**
	 * Identifiers to all available ContentHandlers.
	 */
	public static enum ContentHandlerType{
		Text("TextContentHandler");
		//Add any other type of content handlers if needed
		
		String name;
		ContentHandlerType(String filterName){
			name = filterName;
		}
		
		public String getName() {
			return name;
		}
		
	}

	public static final IContentHandler<?,?> create(ContentHandlerType type) {
		switch (type) {
			case Text:
				return new TextContentHandler();
			default:
				return null;
		}
	}
}
