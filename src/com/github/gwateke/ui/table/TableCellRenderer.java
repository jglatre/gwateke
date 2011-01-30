package com.github.gwateke.ui.table;

import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.Widget;


public interface TableCellRenderer {

	Widget render(HTMLTable table, Object value, int row, int column);
}
