package com.github.gwateke.model.table.support;

import java.util.Collection;

import com.github.gwateke.core.closure.Closure;

import junit.framework.TestCase;


public class RowIdsSelectionHolderTest extends TestCase {

	private static final String[] IDS = {"a", "b", "c", "d", "e", "f", "g"};
	
	private RowIdsSelectionHolder<String> selectionHolder;

	
	public void setUp() {
		selectionHolder = new RowIdsSelectionHolder<String>( new Closure<String, Integer>() {
			public String call(Integer index) {
				return IDS[ index ];
			}			
		} );
		selectionHolder.addSelection(1, 2);
		selectionHolder.addSelection(4, 4);
	}
	
	
	public void testGetSelectedIds() {
		Collection<String> selectedIds = selectionHolder.getSelectedIds();
		assertEquals( 3, selectedIds.size() );
		assertTrue( selectedIds.contains("b") );
		assertTrue( selectedIds.contains("c") );
		assertTrue( selectedIds.contains("e") );
	}
	
	
	public void testGetSelectedRowCount() {
		assertEquals( 3, selectionHolder.getSelectedRowCount() );
	}
	
	
	public void testClearSelection() {
		selectionHolder.clearSelection();
		assertEquals( 0, selectionHolder.getSelectedRowCount() );
	}
	
	
	public void testAddSelection() {
		selectionHolder.addSelection(6, 6);
		assertTrue( selectionHolder.isSelected(6) );
		assertEquals( 4, selectionHolder.getSelectedRowCount() );
	}
}
