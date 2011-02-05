package com.github.gwateke.data.metadata;

import java.util.Set;

import junit.framework.TestCase;


public class MetadataDictionaryTest extends TestCase {

	private MetadataDictionary dict;
	
	
	protected void setUp() {
		dict = MetadataDictionary.fromJson("{ " +
				"foo: {fields: {a: {name: 'A'}, b: {name: 'B'}, c:{name: 'C'}}}, " +
				"bar: {fields: {y: {}, z: {}}}}");
	}
	
	
	public void testEntity() {
		assertNotNull( dict.getEntity("foo") );
		assertNotNull( dict.getEntity("bar") );
		assertNull( dict.getEntity("xxx") );
	}
	
	
	public void testFieldNames() {
		EntityMetadata foo = dict.getEntity("foo");
		Set<String> names = foo.getFieldNames();
		
		assertTrue( names.contains("a") );
		assertTrue( names.contains("b") );
		assertTrue( names.contains("c") );
		assertFalse( names.contains("z") );
	}
	
	
	public void testFieldMetadataName() {
		assertEquals("A", dict.getEntity("foo").getFieldMetadata("a").getName() );
		assertEquals("B", dict.getEntity("foo").getFieldMetadata("b").getName() );
		assertEquals("C", dict.getEntity("foo").getFieldMetadata("c").getName() );
	}
}
