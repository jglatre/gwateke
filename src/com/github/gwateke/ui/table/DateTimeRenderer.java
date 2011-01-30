package com.github.gwateke.ui.table;

import java.util.Date;

import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.Widget;



public class DateTimeRenderer implements TableCellRenderer {
	
	private static final String DEFAULT_FORMAT = "dd-MM-yyyy HH:mm";

	private DateTimeFormat dateTimeFormat;
	
	
	public DateTimeRenderer() {
		this(DEFAULT_FORMAT);
	}
	
	
	public DateTimeRenderer(String format) {
		dateTimeFormat = DateTimeFormat.getFormat(format);
	}
	
	
	public Widget render(HTMLTable table, Object value, int row, int column) {
		String text = value != null ? convertToString(value) : ""; 
		table.setText(row, column, text);
		return null;
	}


	protected String convertToString(Object value) {
		return dateTimeFormat.format((Date) value);
	}

}
