package com.ran488.ws;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.ServletRequestAttributes;

@RestController
public class CustomErrorController implements ErrorController {

	private static final Logger log = LoggerFactory.getLogger(CustomErrorController.class);

	private static final String PATH = "/error";

	@Autowired
	private ErrorAttributes errorAttributes;

	@RequestMapping(value = PATH)
	String error(HttpServletRequest request, HttpServletResponse response) {
		// Appropriate HTTP response code (e.g. 404 or 500) is automatically set
		// by Spring.
		// Here we just define response body.

		StringBuffer retVal = new StringBuffer("HTTP Status: ").append(response.getStatus());

		this.getErrorAttributes(request, true).forEach((key, val) -> {
			retVal.append("<br/>").append(key).append(" = ").append(val);
		});
		log.error("Error occurred hitting an endpoint: " + retVal.toString());
		log.error("Exception?: " + this.getErrorCause(request));
		return retVal.toString();

	}

	@Override
	public String getErrorPath() {
		return PATH;
	}

	private Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
		RequestAttributes requestAttributes = new ServletRequestAttributes(request);
		return errorAttributes.getErrorAttributes(requestAttributes, includeStackTrace);
	}

	private Throwable getErrorCause(HttpServletRequest request) {
		RequestAttributes requestAttributes = new ServletRequestAttributes(request);
		return errorAttributes.getError(requestAttributes);
	}

}
