package com.github.gwateke.client;

import gwtunit.client.GwtTestRunner;
import junit.framework.TestSuite;

import com.github.gwateke.binding.adapter.SelectionInListTest;
import com.github.gwateke.binding.validation.DefaultValidationResultsModelTest;
import com.github.gwateke.binding.validation.DefaultValidationResultsTest;
import com.github.gwateke.binding.value.support.MapKeyAdapterTest;
import com.github.gwateke.binding.value.support.TypeConverterTest;
import com.github.gwateke.binding.value.support.ValueHolderTest;
import com.github.gwateke.command.support.ActionCommandTest;
import com.github.gwateke.command.support.DefaultCommandRegistryTest;
import com.github.gwateke.data.metadata.EntityMetadataTest;
import com.github.gwateke.data.metadata.MetadataDictionaryTest;
import com.github.gwateke.model.event.MessageEventTest;
import com.github.gwateke.model.table.support.RowIdsSelectionHolderTest;
import com.google.gwt.core.client.GWT;


public class AllTests extends GwtTestRunner {

	protected TestSuite suite() {
		TestSuite suite = new TestSuite("gwateke tests");

		suite.addTestSuite( GWT.<SelectionInListTest>create(SelectionInListTest.class) );
		suite.addTestSuite( GWT.<DefaultValidationResultsTest>create(DefaultValidationResultsTest.class) );
		suite.addTestSuite( GWT.<DefaultValidationResultsModelTest>create(DefaultValidationResultsModelTest.class) );
		suite.addTestSuite( GWT.<MapKeyAdapterTest>create(MapKeyAdapterTest.class) );
		suite.addTestSuite( GWT.<TypeConverterTest>create(TypeConverterTest.class) );
		suite.addTestSuite( GWT.<ValueHolderTest>create(ValueHolderTest.class) );
		
		suite.addTestSuite( GWT.<ActionCommandTest>create(ActionCommandTest.class) );
		suite.addTestSuite( GWT.<DefaultCommandRegistryTest>create(DefaultCommandRegistryTest.class) );
		suite.addTestSuite( GWT.<EntityMetadataTest>create(EntityMetadataTest.class) );
		suite.addTestSuite( GWT.<MetadataDictionaryTest>create(MetadataDictionaryTest.class) );
		suite.addTestSuite( GWT.<MessageEventTest>create(MessageEventTest.class) );
		suite.addTestSuite( GWT.<RowIdsSelectionHolderTest>create(RowIdsSelectionHolderTest.class) );
		
		return suite;
	}

}
