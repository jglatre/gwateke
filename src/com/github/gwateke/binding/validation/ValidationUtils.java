package com.github.gwateke.binding.validation;

import java.util.List;

import com.github.gwateke.context.MessageSourceResolvable;
import com.github.gwateke.context.support.DefaultMessageSourceResolvable;
import com.github.gwateke.data.DataError;



public abstract class ValidationUtils {

	/**
	 * Convierte una lista de errores en un ValidationResults.
	 */
	@SuppressWarnings("unchecked")
	public static ValidationResults createValidationMessages(List<DataError> errors) {
		DefaultValidationResults newResults = new DefaultValidationResults();
		
		for (DataError error : errors) {
			String field = error.getField();
			List<String> codes = error.getCodes();
			List arguments = error.getArguments();
			MessageSourceResolvable message = new DefaultMessageSourceResolvable( 
					codes.toArray( new String[codes.size()] ), 
					arguments.toArray( new String[arguments.size()] )
				);
			newResults.addMessage( new DefaultValidationMessage(field, Severity.ERROR, message) );
		}
		
		return newResults;
	}
	
	
	public static String toString(List<DataError> errors) {
		ValidationResults results = createValidationMessages(errors);
		StringBuffer buf = new StringBuffer();
		for (Object message: results.getMessages()) {
			buf.append( ((ValidationMessage) message).getMessage() ).append('\n');
		}
		return buf.toString();
	}

}
