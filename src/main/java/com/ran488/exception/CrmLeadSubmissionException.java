package com.ran488.exception;

/**
 * Domain level Exception to wrap underlying/root cause exceptions. Parent
 * constructors that do no include the cause have been omitted because we want
 * the entire stack trace(e.g. caused by...).
 */
public class CrmLeadSubmissionException extends Exception {

	private static final long serialVersionUID = 5557921625787033274L;

	public CrmLeadSubmissionException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public CrmLeadSubmissionException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

}
