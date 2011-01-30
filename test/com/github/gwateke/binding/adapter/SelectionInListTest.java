package com.github.gwateke.binding.adapter;

import java.util.Arrays;
import java.util.List;

import junit.framework.TestCase;

import com.github.gwateke.binding.value.ValueModel;
import com.github.gwateke.binding.value.support.ValueHolder;


public class SelectionInListTest extends TestCase {
	
	private static List<String> list = Arrays.asList(new String[] {"a", "b", "c", "d"});

	
	public void testInitialSelection() {
		ValueModel<String> vm = new ValueHolder<String>("d");
		SelectionInList sil = new SelectionInList<String>(list, vm);
		
		assertEquals(3, sil.getSelectionIndexHolder().getValue());
	}
	
	
	public void testChangeSelection() {
		ValueModel<String> vm = new ValueHolder<String>();
		SelectionInList sil = new SelectionInList<String>(list, vm);
		ValueModel<Integer> index = sil.getSelectionIndexHolder();
		
		vm.setValue("a");
		assertEquals(Integer.valueOf(0), index.getValue());
		vm.setValue("b");
		assertEquals(Integer.valueOf(1), index.getValue());
		vm.setValue("c");
		assertEquals(Integer.valueOf(2), index.getValue());
		vm.setValue("d");
		assertEquals(Integer.valueOf(3), index.getValue());
		vm.setValue("xxx");
		assertEquals(Integer.valueOf(-1), index.getValue());
	}

	
	public void testChangeIndex() {
		ValueModel<String> vm = new ValueHolder<String>();
		SelectionInList<String> sil = new SelectionInList<String>(list, vm);
		ValueModel<Integer> index = sil.getSelectionIndexHolder();
		
		index.setValue(0);
		assertEquals("a", vm.getValue());
		index.setValue(1);
		assertEquals("b", vm.getValue());
		index.setValue(2);
		assertEquals("c", vm.getValue());
		index.setValue(3);
		assertEquals("d", vm.getValue());
		index.setValue(4);
		assertEquals(null, vm.getValue());
	}

}
