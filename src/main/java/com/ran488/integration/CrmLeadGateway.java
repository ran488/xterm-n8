package com.ran488.integration;

import com.ran488.exception.CrmLeadSubmissionException;

public interface CrmLeadGateway<T> {

	/** Submit the lead to the CRM system. 
	 * 
	 */
	T submit(final T message) throws CrmLeadSubmissionException;
}
