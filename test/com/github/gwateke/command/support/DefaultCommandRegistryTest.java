package com.github.gwateke.command.support;

import junit.framework.TestCase;

import com.github.gwateke.command.ActionCommand;

public class DefaultCommandRegistryTest extends TestCase {

	private DefaultCommandRegistry registry;
	
	
	protected void setUp() throws Exception {
		registry = new DefaultCommandRegistry();
		registry.registerCommand( new ActionCommand("xyz") {
			@Override
			protected void doExecuteCommand() {
			}			
		});
	}
	
	
	public void testContainsActionCommand() {
		assertTrue( registry.containsActionCommand("xyz") );
		assertFalse( registry.containsActionCommand("unknown command") );
	}
	
	
	public void testGetActionCommand() {
		assertNotNull( registry.getActionCommand("xyz") );
		assertNull( registry.getActionCommand("unknown command") );		
	}

}
