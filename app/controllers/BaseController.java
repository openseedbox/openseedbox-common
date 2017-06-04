package controllers;

import com.openseedbox.code.MessageException;
import com.openseedbox.code.Util;
import com.openseedbox.mvc.GenericResult;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.h2.util.StringUtils;
import play.Play;
import play.Play.Mode;
import play.mvc.Before;
import play.mvc.Catch;
import play.mvc.Controller;
import play.mvc.Http;
import play.templates.Template;
import play.templates.TemplateLoader;

public abstract class BaseController extends Controller {
	
	@Before
	public static void checkRequestSecure() {
		//this is required so Play! knows if its in front of https-secured nginx or not
		Http.Header secure = request.headers.get("x-forwarded-proto");
		if (secure != null) {
			if (secure.value().toLowerCase().equals("https")) {
				request.secure = true;
			}
		}
	}	
	
	protected static void setGeneralErrorMessage(String message) {
		flash.put("error", message);
	}

	protected static void setGeneralErrorMessage(Exception ex) {
		setGeneralErrorMessage(Util.getStackTrace(ex));
	}

	protected static void setGeneralMessage(String message) {
		flash.put("message", message);
	}
	
	protected static void setGeneralWarningMessage(String message) {
		flash.put("warning", message);
	}

	protected static String renderToString(String template) {
		return renderToString(template, new HashMap<String, Object>());
	}

	protected static String renderToString(String template, Map<String, Object> args) {
		Template t = TemplateLoader.load(template);
		try {
			if (args != null) {
				renderArgs.data.putAll(args);	
			}			
			return t.render(renderArgs.data);
		} catch (Exception ex) {
			return ExceptionUtils.getStackTrace(ex);
		}
	}

	protected static void result(Object o) {
		throw new GenericResult(o);
	}

	protected static void resultTemplate(String name) {
		Template t = TemplateLoader.load(name);
		throw new GenericResult(t.render());
	}

	protected static void resultError(String message) {
		throw new GenericResult(message, true);
	}

	protected static void resultError(Exception ex) {
		resultError(Util.getStackTrace(ex));
	}

	protected static void write(String s) {
		write(s, new Object[]{});
	}

	protected static void write(String s, Object... args) {
		response.writeChunk(String.format(s, args));
	}

	protected static void writeLine(String s) {
		write(s + "<br />");
	}

	protected static void writeLine(String s, Object... args) {
		write(s + "<br />", args);
	}

	@Catch(Exception.class)
	protected static void onException(Exception ex) throws Exception {
		if (ex instanceof MessageException) {
			if (!StringUtils.equals(params.get("ext"), "html")) {
				resultError(ex);
			}
			result(Util.getStackTrace(ex));
		} else {
			if (Play.mode == Mode.DEV) {
				ex.printStackTrace();
				throw ex;
			} else {
				//Mails.sendError(ex, request);
			}			
		}
	}
}