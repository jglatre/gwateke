package com.github.gwateke.binding.value.support;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import com.github.gwateke.binding.value.ValueModel;


public class MapKeyAdapterTest extends TestCase {
		
	private ValueModel<Object> mapKeyValue;
	private ValueModel<Object> mapKeyNested;

	
	protected void setUp() {
		Map<String,Object> mapB = new HashMap<String,Object>();
		mapB.put("c", 1);

		Map<String,Object> map = new HashMap<String,Object>();
		map.put("a", 0);
		map.put("b", mapB);
		
		ValueModel<Map<String,Object>> mapValue = new ValueHolder<Map<String,Object>>(map);
		
		mapKeyValue = new MapKeyAdapter<String,Object>(mapValue, "a");
		mapKeyNested = new MapKeyAdapter<String,Object>(mapValue, "b.c");
	}
	
	
	public void testGetValue() {
		assertEquals(0, mapKeyValue.getValue());
	}
	
	
	public void testGetNestedValue() {
		assertEquals(1, mapKeyNested.getValue());
	}
	

	public void testSetValue() {
		mapKeyValue.addValueChangeListener(new ChangeAsserter(new Integer(0), "XXX"));		
		mapKeyValue.setValue("XXX");
		assertEquals("XXX", mapKeyValue.getValue());
	}


	public void testSetNestedValue() {
		mapKeyNested.addValueChangeListener(new ChangeAsserter(new Integer(1), "---"));		
		mapKeyNested.setValue("---");
		assertEquals("---", mapKeyNested.getValue());
	}


	
	private static class ChangeAsserter implements PropertyChangeListener {
		private Object oldValue;
		private Object newValue;
		
		public ChangeAsserter(Object oldValue, Object newValue) {
			this.oldValue = oldValue;
			this.newValue = newValue;
		}
		
		public void propertyChange(PropertyChangeEvent evt) {
			assertEquals(oldValue, evt.getOldValue());
			assertEquals(newValue, evt.getNewValue());
		}
	}

}
