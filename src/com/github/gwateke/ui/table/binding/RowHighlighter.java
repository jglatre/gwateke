package com.github.gwateke.ui.table.binding;


import com.github.gwateke.binding.value.ValueModel;
import com.github.gwateke.binding.value.support.AbstractValueModelAdapter;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.HTMLTable.RowFormatter;


/**
 * Binding between a table row and a boolean ValueModel.
 * 
 * @author juanjo
 */
public class RowHighlighter extends AbstractValueModelAdapter<Boolean> { 
	
	private static final String DEFAULT_HIGHLIGHT_STYLE = "table-SelectedRow";
	
	private int row;
	private RowFormatter formatter;
	private String highlightStyle = DEFAULT_HIGHLIGHT_STYLE;
	
	
	public RowHighlighter(int row, HTMLTable table, ValueModel<Boolean> valueModel) {
		super(valueModel);
		this.row = row;
		this.formatter = table.getRowFormatter();
		initalizeAdaptedValue();
	}
	
	
	public void refresh() {
		initalizeAdaptedValue();
	}

	
	protected void valueModelValueChanged(Boolean newValue) {
		setHighLight(newValue);
	}
	
	
	protected void setHighLight(boolean highlight) {
		if (highlight) {
			formatter.addStyleName(row, highlightStyle);
		}
		else {
			formatter.removeStyleName(row, highlightStyle);
		}
	}
	
}