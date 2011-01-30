package com.github.gwateke.client;

import gwtunit.client.GwtTestRunner;

import javax.swing.DefaultListSelectionModelTest;

import junit.framework.TestSuite;

import com.github.gwateke.binding.adapter.SelectionInListTest;
import com.github.gwateke.binding.validation.DefaultValidationResultsModelTest;
import com.github.gwateke.binding.validation.DefaultValidationResultsTest;
import com.github.gwateke.binding.value.support.MapKeyAdapterTest;
import com.github.gwateke.binding.value.support.TypeConverterTest;
import com.github.gwateke.binding.value.support.ValueHolderTest;
import com.google.gwt.core.client.GWT;


public class AllTests extends GwtTestRunner {

	protected TestSuite suite() {
		TestSuite suite = new TestSuite("gwateke tests");

		suite.addTestSuite( GWT.<DefaultListSelectionModelTest>create(DefaultListSelectionModelTest.class) );
		suite.addTestSuite( GWT.<SelectionInListTest>create(SelectionInListTest.class) );
		suite.addTestSuite( GWT.<DefaultValidationResultsTest>create(DefaultValidationResultsTest.class) );
		suite.addTestSuite( GWT.<DefaultValidationResultsModelTest>create(DefaultValidationResultsModelTest.class) );
		suite.addTestSuite( GWT.<MapKeyAdapterTest>create(MapKeyAdapterTest.class) );
		suite.addTestSuite( GWT.<TypeConverterTest>create( TypeConverterTest.class) );
		suite.addTestSuite( GWT.<ValueHolderTest>create(ValueHolderTest.class) );
		
		return suite;
	}

}
