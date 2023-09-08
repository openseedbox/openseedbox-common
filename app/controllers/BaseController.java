package controllers;

import com.openseedbox.code.MessageException;
import com.openseedbox.code.Util;
import com.openseedbox.mvc.GenericResult;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.apache.commons.lang3.exception.ExceptionUtils;
import play.Play;
import play.Play.Mode;
import play.mvc.Before;
import play.mvc.Catch;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.results.Error;
import play.mvc.results.Ok;
import play.mvc.results.Result;
import play.templates.Template;
import play.templates.TemplateLoader;

public abstract class BaseController extends Controller {
	
	@Before
	public Result checkRequestSecure() {
		//this is required so Play! knows if its in front of https-secured nginx or not
		Http.Header secure = request.headers.get("x-forwarded-proto");
		if (secure != null) {
			if (secure.value().toLowerCase().equals("https")) {
				request.secure = true;
			}
		}
		return null;
	}
	
	protected Result setGeneralErrorMessage(String message) {
		flash.put("error", message);
		return null;
	}

	protected Result setGeneralErrorMessage(Exception ex) {
		return setGeneralErrorMessage(Util.getStackTrace(ex));
	}

	protected Result setGeneralMessage(String message) {
		flash.put("message", message);
		return null;
	}
	
	protected Result setGeneralWarningMessage(String message) {
		flash.put("warning", message);
		return null;
	}

	protected String renderToString(String template) {
		return renderToString(template, new HashMap<String, Object>());
	}

	protected String renderToString(String template, Map<String, Object> args) {
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

	protected Result result(Object o) {
		return new GenericResult(o);
	}

	protected Result resultTemplate(String name) {
		Template t = TemplateLoader.load(name);
		return new GenericResult(t.render());
	}

	protected Result resultError(String message) {
		return new GenericResult(message, true);
	}

	protected Result resultError(Exception ex) {
		return resultError(Util.getStackTrace(ex));
	}

	protected void write(String s) {
		write(s, new Object[]{});
	}

	protected void write(String s, Object... args) {
		response.writeChunk(String.format(s, args));
	}

	protected void writeLine(String s) {
		write(s + "<br />");
	}

	protected void writeLine(String s, Object... args) {
		write(s + "<br />", args);
	}

	@Catch(Exception.class)
	protected Result onException(Exception ex) throws Exception {
		if (ex instanceof MessageException) {
			if (!Objects.equals(params.get("ext"), "html")) {
				return resultError(ex);
			}
			return result(Util.getStackTrace(ex));
		} else {
			if (Play.mode == Mode.DEV) {
				ex.printStackTrace();
				throw ex;
			} else {
				//Mails.sendError(ex, request);
			}			
		}
		return new Ok();
	}
}