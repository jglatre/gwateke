package com.github.gwateke.ui.table;


import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.HTMLTable;
import com.google.gwt.user.client.ui.Widget;


public class BooleanCellRenderer implements TableCellRenderer {

	private AbstractImagePrototype truePrototype;
	private AbstractImagePrototype falsePrototype;
		
	
	public BooleanCellRenderer() {
	}
	
	
	public BooleanCellRenderer(AbstractImagePrototype truePrototype, AbstractImagePrototype falsePrototype) {
		this.truePrototype = truePrototype;
		this.falsePrototype = falsePrototype;
	}


	public Widget render(HTMLTable table, Object value, int row, int column) {
		if (value != null && ((Boolean) value)) {
			table.setWidget(row, column, createTrueWidget());
		}
		else {
			table.setWidget(row, column, createFalseWidget());			
		}
		return null;
	}

	
	protected Widget createTrueWidget(){
		return truePrototype.createImage();
	}

	
	protected Widget createFalseWidget() {
		return falsePrototype.createImage();
	}
}
