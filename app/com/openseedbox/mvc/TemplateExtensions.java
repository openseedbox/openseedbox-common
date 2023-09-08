package com.openseedbox.mvc;

import org.apache.commons.lang3.StringUtils;
import play.templates.JavaExtensions;

/**
 * Some helper methods to make working with Rythm templates a bit easier
 * @author Erin Drummond
 */
public class TemplateExtensions extends JavaExtensions {
	
	/**
	 * Checks if the specified string is empty. If it is, a default value is returned.
	 * @param s The string to check
	 * @param d The default value if the string is empty
	 * @return Either the string or the default value
	 */
	public static String empty(String s, String d) {
		if (StringUtils.isEmpty(s)) {
			return d;
		}
		return s;
	}
	
	/**
	 * Returns true if the specified string is empty
	 * @param s The string to check
	 * @return True if its empty
	 */
	public static boolean isEmpty(String s) {
		return StringUtils.isEmpty(s);
	}
	
	/**
	 * Trims the specified string to the specified length if its larger
	 * or leave it along if its not
	 * @param s The string to trim
	 * @param length The length to trim to
	 * @return The trimmed string
	 */
	public static String trimTo(String s, int length) {
		if (s.length() > length) {
			s = s.substring(0, length - "...".length());
			s += "...";
		}
		return s;
	}
	
	/**
	 * Returns the url for the specified asset (prepends assets.prefix from the configuration file)
	 * @param s The asset url
	 * @return The final url
	 */
	public static String asset(String s) {
		if (s.equals("ajax")) {
			return getAssetUrl("/images/ajax-loader.gif");
		}
		return getAssetUrl(s);
	}
	
	private static String getAssetUrl(String url) {
		if (StringUtils.isEmpty(url)) {
			return "empty url";
		}
		String assetsPrefix = play.Play.configuration.getProperty("assets.prefix", "/public/");
		if (!assetsPrefix.endsWith("/")) {
			assetsPrefix += "/";
		}
		if (url.startsWith("/")) {
			url = url.substring(1);
		}
		return (assetsPrefix + url).trim();		
	}	
	
}
