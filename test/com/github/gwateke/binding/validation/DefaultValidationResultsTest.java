package com.github.gwateke.binding.validation;

import junit.framework.TestCase;


public class DefaultValidationResultsTest extends TestCase {
	
	private DefaultValidationResults results;

	
	protected void setUp() throws Exception {
		results = new DefaultValidationResults();
	}

	
	public void testAddMessage() {
		results.addMessage("x", Severity.INFO, "xxxx");
		
		assertTrue(results.getHasInfo());
		assertFalse(results.getHasWarnings());
		assertFalse(results.getHasErrors());
		assertEquals(1, results.getMessageCount());

		results.addMessage("y", Severity.WARNING, "yyyy");
		
		assertTrue(results.getHasInfo());
		assertTrue(results.getHasWarnings());
		assertFalse(results.getHasErrors());
		assertEquals(2, results.getMessageCount());

		results.addMessage("z", Severity.ERROR, "zzzz");
		
		assertTrue(results.getHasInfo());
		assertTrue(results.getHasWarnings());
		assertTrue(results.getHasErrors());
		assertEquals(3, results.getMessageCount());
	}
	
}
