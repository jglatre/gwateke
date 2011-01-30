package com.github.gwateke.binding.value.support;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import junit.framework.TestCase;

import com.github.gwateke.binding.value.ValueModel;


public class ValueHolderTest extends TestCase {
	
	public void testSetValue() {
		ValueModel<Boolean> v = new ValueHolder<Boolean>(true);
		
		ChangeEventCatcher catcher = new ChangeEventCatcher();
		v.addValueChangeListener(catcher);
		v.setValue(false);
		
		assertFalse( v.getValue() );
		assertNotNull("Change not detected!", catcher.event);
		assertEquals(catcher.event.getOldValue(), true);
		assertEquals(catcher.event.getNewValue(), false);		
	}
	
	
	private static class ChangeEventCatcher implements PropertyChangeListener {
		PropertyChangeEvent event;
		
		public void propertyChange(PropertyChangeEvent evt) {
			event = evt;
		}
	}
}
