package com.github.gwateke.command.support;

import com.github.gwateke.command.ActionCommand;

import junit.framework.TestCase;

public class ActionCommandTest extends TestCase {

	private ActionCommandMock command;
	
	
	protected void setUp() throws Exception {
		command = new ActionCommandMock("mock");
	}
	
	
	public void testGetId() {
		assertEquals("mock", command.getId());
	}
	
	
	public void testExecute() {
		assertFalse( command.executed );
		command.execute();
		assertTrue( command.executed );
		
	}
	
	private static class ActionCommandMock extends ActionCommand {
		boolean executed = false;		
		
		@Override
		protected void doExecuteCommand() {
			executed = true;
		}
		
		public ActionCommandMock(String commandId) {
			super(commandId);
		}
	}

}
