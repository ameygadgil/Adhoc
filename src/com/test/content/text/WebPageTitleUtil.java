package com.test.content.text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author Amey Gadgil
 * 
 * Ugly utility method to get title of html page. Need to find a better way. Done quickly for assignment.
 * Referred some samples to get this done.
 *
 */
public class WebPageTitleUtil {

	private static final Pattern HTML_TITLE_PATTERN = Pattern.compile(
			"\\<title>(.*)\\</title>", Pattern.CASE_INSENSITIVE
					| Pattern.DOTALL);

	/**
	 * @param http
	 * @return extracted title of page from html.
	 * @throws IOException
	 */
	public static String getPageTitle(String linkUrl) throws IOException {
		URL url = new URL(linkUrl);
		URLConnection conn = null;
		try {

			conn = url.openConnection();

			String type = conn.getContentType();
			if (type != null && !type.toLowerCase().contains("text/html"))
				return null; // if not html, we can't get title. Return from here.
			else {
				// read response stream
				BufferedReader bufReader = new BufferedReader(
						new InputStreamReader(conn.getInputStream(),
								Charset.defaultCharset()));
				int n = 0;
				int readCount = 0;
				char[] buf = new char[1024];
				StringBuilder content = new StringBuilder();

				// read till end of file or 8 times the buffer (no need to read
				// all data, assuming we get content in first 8 reads)
				while (readCount < 8
						&& (n = bufReader.read(buf, 0, buf.length)) != -1) {
					content.append(buf, 0, n);
					readCount++;
				}
				bufReader.close();

				// extract the title
				Matcher matcher = HTML_TITLE_PATTERN.matcher(content);
				if (matcher.find()) {

					// replace whitespaces and HTML brackets

					return matcher.group(1).replaceAll("[\\s\\<>]+", " ")
							.trim();
				} else
					return null;
			}
		} finally {
			//any cleanup?
		}
	}
}
