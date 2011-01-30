package com.github.gwateke.binding.value.support;

import junit.framework.TestCase;

import com.github.gwateke.binding.value.ValueModel;
import com.github.gwateke.core.closure.Closure;


public class TypeConverterTest extends TestCase {

	private ValueModel<Double> v;
	private ValueModel<Object> c;
	

	protected void setUp() throws Exception {
		v = new ValueHolder<Double>(0d);
		c = new TypeConverter(v,
				new Closure<String, Double>() {
					public String call(Double value) { return String.valueOf(value); }
				},
				new Closure<Double, String>() {
					public Double call(String value) { return new Double(value); }
				});
	}

	protected void tearDown() throws Exception {
	}

	
	public void testGetValue() {
		assertEquals("0", c.getValue());
		
		v.setValue(1.2345d);
		assertEquals("1.2345", c.getValue());
	}
	
	
	public void testSetValue() {
		c.setValue("0.12");
		
		assertEquals(0.12d, v.getValue());
	}
}
