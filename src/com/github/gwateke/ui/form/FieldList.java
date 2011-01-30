package com.github.gwateke.ui.form;

import java.util.ArrayList;
import java.util.List;

import com.github.gwateke.context.MessageSource;
import com.github.gwateke.core.closure.Closure;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Anchor;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Widget;


public class FieldList extends Composite {

	private List<Item> items = new ArrayList<Item>();
	private FlexTable innerPanel = new FlexTable();
	private Anchor addItem;
	private MessageSource messageSource;
	private Closure<Widget, ?> fieldBuilder;

	
	public FieldList(MessageSource messageSource) {
		this.messageSource = messageSource;
		this.addItem = new Anchor( messageSource.getMessage("textlist.add") );
		
		this.addItem.addClickHandler( new ClickHandler() {
			public void onClick(ClickEvent event) {
				addItem();
			}			
		});
		
		innerPanel.setCellSpacing(2);
		innerPanel.setStyleName("textlist");
		innerPanel.setWidget(0, 0, addItem);
		
		initWidget(innerPanel);
	}

	
	public void setFieldBuilder(Closure<Widget, ?> fieldBuilder) {
		this.fieldBuilder = fieldBuilder;
	}


	public void addItem() {
		int pos = innerPanel.getRowCount() - 1; 
		Item item = new Item(innerPanel);
		item.putAt(pos);
		items.add(item);
	}
	
	
	private class Item {
		private FlexTable table;
		private Widget field;
		private Anchor remove;
		
		public Item(FlexTable table) {
			this.table = table;
			this.field = fieldBuilder.call(null);
			this.remove = new Anchor( messageSource.getMessage("textlist.remove") );
			
			this.remove.addClickHandler( new ClickHandler() {
				public void onClick(ClickEvent event) {
					remove();
				}				
			} );
		}
		
		
		public void putAt(int row) {
			table.insertRow(row);
			table.setWidget(row, 0, field);
			table.setWidget(row, 1, remove);	
		}
				
		
		public void remove() {
			table.removeRow( getTableRow() );	
		}
		
		
		private int getTableRow() {
			for (int row = 0; row < table.getRowCount(); row++) {
				if (table.getWidget(row, 0) == field) {
					return row;
				}
			}
			return -1;
		}
	}
}
