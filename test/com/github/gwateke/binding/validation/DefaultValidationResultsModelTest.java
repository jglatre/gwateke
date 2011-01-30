package com.github.gwateke.binding.validation;

import junit.framework.TestCase;


public class DefaultValidationResultsModelTest extends TestCase {

	private DefaultValidationResultsModel model;

	
	protected void setUp() throws Exception {
		model = new DefaultValidationResultsModel();
	}
	

	public void testUpdateValidationResults() {
		DefaultValidationResults results = new DefaultValidationResults();
		results.addMessage("x", Severity.ERROR, "xxxx");

		ErrorListener listener1 = new ErrorListener(1);
		model.addValidationListener(listener1);
		
		model.updateValidationResults(results);
		
		model.removeValidationListener(listener1);
		ErrorListener listener2 = new ErrorListener(0);
		model.addValidationListener(listener2);
		
		model.updateValidationResults( new DefaultValidationResults() );
	}

	
	private class ErrorListener implements ValidationListener {
		private int count;
		
		public ErrorListener(int count) {
			this.count = count;
		}
		
		public void validationResultsChanged(ValidationResults results) {
			assertEquals(count, results.getMessageCount());
		}
	}

}
