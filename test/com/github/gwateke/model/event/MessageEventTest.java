package com.github.gwateke.model.event;

import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.event.shared.HandlerRegistration;

import junit.framework.TestCase;


public class MessageEventTest extends TestCase {

	private HandlerManager manager;
	private MessageCatcher catcher = new MessageCatcher();
	private HandlerRegistration registration;
	
	
	protected void setUp() {
		manager = new HandlerManager(this);
		registration = manager.addHandler(MessageEvent.getType(), catcher);
	}
	
	
	public void testFire() {
		manager.fireEvent( new MessageEvent("xxx") );
		
		assertNotNull( catcher.event );
		assertEquals( "xxx", catcher.event.getMessageSourceResolvable().getDefaultMessage() );
	}
	
	
	private class MessageCatcher implements MessageHandler {
		private MessageEvent event;
		
		public void onMessage(MessageEvent event) {
			this.event = event;
			assertEquals( MessageEventTest.this, event.getSource() );
		}
	}
}
