package com.github.gwateke.ui.table;

import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.Widget;


public class AnchorCellRenderer implements TableCellRenderer {

	public Widget render(HTMLTable table, Object value, int row, int column) {
		String[] attrs = (String[]) value; 
		String html = "<a href=\"" + attrs[0] + "\" title=\"" + attrs[2] + "\">" +
						attrs[1] + "</a>"; 
		table.setHTML(row, column, html);
		return null;
	}

}
