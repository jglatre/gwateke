package com.github.gwateke.client;

import javax.swing.DefaultListSelectionModelTest;

import com.google.gwt.core.client.GWT;

import gwtunit.client.GwtTestRunner;

import junit.framework.TestSuite;


public class AllTests extends GwtTestRunner {

	protected TestSuite suite() {
		TestSuite suite = new TestSuite("gwateke tests");

		suite.addTestSuite( GWT.<DefaultListSelectionModelTest>create(DefaultListSelectionModelTest.class) );
		
		return suite;
	}

}
