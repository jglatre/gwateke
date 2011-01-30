package com.github.gwateke.ui.table;

import com.github.gwateke.context.MessageSource;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.Widget;


public class I18nCellRenderer implements TableCellRenderer {

	private String keyPrefix;
	private MessageSource messageSource;
	
	
	public I18nCellRenderer(MessageSource messageSource, String keyPrefix) {
		this.messageSource = messageSource;
		this.keyPrefix = keyPrefix;
	}

	
	public Widget render(HTMLTable table, Object value, int row, int column) {
		String text = value != null ? messageSource.getMessage(keyPrefix + '.' + value) : ""; 
		table.setText(row, column, text);
		return null;
	}

}
