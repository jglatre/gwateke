package com.github.gwateke.data.metadata;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import junit.framework.TestCase;

public class EntityMetadataTest extends TestCase {

	private EntityMetadata entity;
	
	
	protected void setUp() throws Exception {
		entity = EntityMetadata.fromJson(
				"{actions: ['x', 'y', 'z']," +
				" fields: {" +
				"	a: {name: 'a', type: 'java.lang.Long'}," +
				"	b: {name: 'b'}," +
				"	c: {name: 'c'}" +
				"}}");
	}


	public void testGetAllowedAction() {
		List<String> actions = entity.getAllowedActions();
		assertEquals( 3, actions.size() );
		assertEquals( "x", actions.get(0) );
		assertEquals( "y", actions.get(1) );
		assertEquals( "z", actions.get(2) );
	}
	
	
	public void testGetFieldNames() {
		Set<String> fields = entity.getFieldNames();
		assertEquals( 3, fields.size() );
		assertTrue( fields.contains("a") );
		assertTrue( fields.contains("b") );
		assertTrue( fields.contains("c") );
	}
	
	
	public void testGetFieldMetadata() {
		FieldMetadata fieldA = entity.getFieldMetadata("a");
		assertNotNull(fieldA);
		assertEquals( "a", fieldA.getName() );
		assertEquals( "java.lang.Long", fieldA.getType() );
	}
	
	
	public void testIterator() {
		Iterator<FieldMetadata> fields = entity.iterator();
		assertEquals( "a", fields.next().getName() );
		assertEquals( "b", fields.next().getName() );
		assertEquals( "c", fields.next().getName() );
		assertFalse( fields.hasNext() );
	}
	
	
	public void testGetAllUserMetadata() {
		Map<String, ?> fieldA = entity.getAllUserMetadata("a");
		assertEquals( 2, fieldA.size() );
		assertEquals( "a", fieldA.get("name") );
		assertEquals( "java.lang.Long", fieldA.get("type") );
	}
}
