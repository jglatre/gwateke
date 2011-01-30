package com.github.gwateke.ui.table;

import static com.google.gwt.user.client.ui.HasHorizontalAlignment.ALIGN_RIGHT;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.github.gwateke.binding.convert.ConversionService;
import com.github.gwateke.binding.convert.support.DefaultConversionService;
import com.github.gwateke.core.closure.Closure;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;


public class DefaultCellRenderer implements TableCellRenderer {
		
	private static final ConversionService conversionService = new DefaultConversionService();

	protected final Log log = LogFactory.getLog(getClass());

	
	public Widget render(HTMLTable table, Object value, int row, int column) {
		Label label = new Label();
		label.setText( convertToString(value) );
		if (value instanceof Number) {
			label.setHorizontalAlignment(ALIGN_RIGHT);
		}
		table.setWidget(row, column, label);

		return null;
	}
	
	
	protected String convertToString(Object value) {
		if (value == null) {
			return "";
		}
		else if (value instanceof String) {
			return value.toString();
		} 
		else {
			//TODO fijar converter en el constructor
			Closure converter = conversionService.getConversionClosure(value.getClass(), String.class);
			if (converter == null) {
				log.warn("No converter for " + value.getClass().getName());
				return String.valueOf(value);
			}
			
			return (String) converter.call(value);			
		}
	}
	
}