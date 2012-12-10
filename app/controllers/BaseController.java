package controllers;

import com.openseedbox.mvc.GenericResult;
import com.openseedbox.code.MessageException;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.lang.exception.ExceptionUtils;
import play.Play;
import play.Play.Mode;
import play.data.validation.Validation;
import play.mvc.Catch;
import play.mvc.Controller;
import play.templates.Template;
import play.templates.TemplateLoader;

public abstract class BaseController extends Controller {
	
	protected static void addGeneralError(Exception ex) {
		Validation.addError("general", ex.getMessage());
	}
	
	protected static void setGeneralErrorMessage(String message) {
		addGeneralError(new MessageException(message));
	}

	protected static void setGeneralMessage(String message) {
		flash.put("message", message);
	}
	
	protected static void setGeneralWarningMessage(String message) {
		flash.put("warning", message);
	}

	protected static String getServerPath() {
		return String.format("http://%s", request.host);
	}

	protected static String renderToString(String template) {
		return renderToString(template, new HashMap<String, Object>());
	}

	protected static String renderToString(String template, Map<String, Object> args) {
		Template t = TemplateLoader.load(template);
		try {
			return t.render(args);
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
	
	protected static void write(String s) {
		write(s, new Object[]{});
	}

	protected static void write(String s, Object... args) {
		response.writeChunk(String.format(s, args));
	}

	protected static void writeLine(String s) {
		write(s + "\n");
	}

	protected static void writeLine(String s, Object... args) {
		write(s + "\n", args);
	}

	@Catch(Exception.class)
	protected static void onException(Exception ex) throws Exception {
		if (ex instanceof MessageException) {
			if (params.get("ext") != null && params.get("ext").equals("json")) {
				resultError(ex.getMessage());
			}
			result(ex.getMessage());
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