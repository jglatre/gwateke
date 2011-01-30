package com.github.gwateke.ui.table;

import com.google.gwt.i18n.client.NumberFormat;


public class NumberFormatCellRenderer extends DefaultCellRenderer {

	private final NumberFormat format;
	
	
	public NumberFormatCellRenderer(NumberFormat format) {
		this.format = format;
	}
	
	
	@Override
	protected String convertToString(Object value) {
		if (value instanceof Number) {
			double number = ((Number) value).doubleValue();
			return Double.isNaN(number) ? "" : format.format(number);
		}
		else {
			return String.valueOf(value);
		}
	}

}
