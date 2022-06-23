package com.openseedbox.mvc.tags;

import groovy.lang.Closure;
import java.io.PrintWriter;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import play.Play;
import play.templates.FastTags;
import play.templates.GroovyTemplate.ExecutableTemplate;

@FastTags.Namespace("asset")
public class AssetFastTag extends FastTags {
	
	public static void _url(Map<?,?> args, Closure body, PrintWriter out, ExecutableTemplate template, int fromLine) {
		String url = getAssetUrl((String) args.get("arg"));
		out.print(url);
	}
	
	public static void _ajax(Map<?,?> args, Closure body, PrintWriter out, ExecutableTemplate template, int fromLine) {
		out.print(getAssetUrl("/images/ajax-loader.gif"));
	}	
	
	private static String getAssetUrl(String url) {
		if (StringUtils.isEmpty(url)) {
			return "empty url";
		}		
		String assetsPrefix = Play.configuration.getProperty("openseedbox.assets.prefix", "/public");
		if (!assetsPrefix.endsWith("/")) {
			assetsPrefix += "/";
		}
		final String contextPathStr = "openseedbox.context.path";
		final String contextPathProp = "${" + contextPathStr + "}";
		if (assetsPrefix.startsWith(contextPathProp)) {
			assetsPrefix = assetsPrefix.replace(contextPathProp, play.Play.configuration.getProperty(contextPathStr, ""));
		}
		if (url.startsWith("/")) {
			url = url.substring(1);
		}
		return (assetsPrefix + url).trim();		
	}
	
}
