package com.github.gwateke.ui.form;

import java.util.List;
import java.util.Vector;

import com.github.gwateke.context.MessageSource;
import com.google.gwt.user.client.ui.ChangeListener;
import com.google.gwt.user.client.ui.ChangeListenerCollection;
import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.SourcesChangeEvents;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;


/**
 * Lista editable de textos.
 * 
 * @author juanjo
 */
public class TextList extends Composite implements ClickListener, SourcesChangeEvents {

	private List<ItemEditor> items = new Vector<ItemEditor>();
	private FlexTable innerPanel = new FlexTable();
	private Hyperlink addItem;
	private ChangeListenerCollection changeListeners;
	private MessageSource messageSource;

	
	public TextList(MessageSource messageSource) {
		this.messageSource = messageSource;

		addItem = new Hyperlink( messageSource.getMessage("textlist.add"), "" );
		addItem.addClickListener(this);
		
		innerPanel.setCellSpacing(2);
		innerPanel.setStyleName("textlist");
		innerPanel.setWidget(0, 0, addItem);
		
		initWidget(innerPanel);
	}
	
	
	public void onClick(Widget sender) {
		if (sender == addItem) {
			addItem("");
			fireChange();
		}
	}
	
	
	public void addItem(String text) {
		addItemEditor().setValue(text);
	}
	

	public String[] getItems() {
		String itemsArray[] = new String[ items.size() ];
		
		for (int i = 0; i < items.size(); i++) {
			itemsArray[ i ] = items.get(i).getValue();
		}
		
		return itemsArray;
	}

	
	public void setItems(String[] itemsArray) {
		int diff = itemsArray.length - items.size();
		if (diff > 0) {			
			for (int i = 0; i < diff; i++) {
				addItem("");
			}
		}
		else if (diff < 0) {
			for (int i = items.size() - 1; i >= itemsArray.length; i--) {
				ItemEditor item = items.get(i);
				item.remove();
				items.remove(i);
			}
		}
		
		for (int i = 0; i < itemsArray.length; i++) {
			items.get(i).setValue( itemsArray[i] );
		}
	}
	

	public void addChangeListener(ChangeListener listener) {
		if (changeListeners == null) {
			changeListeners = new ChangeListenerCollection();
		}
		changeListeners.add(listener);
	}


	public void removeChangeListener(ChangeListener listener) {
		if (changeListeners != null) {
			changeListeners.remove(listener);
		}
	}
	
	//-----------------------------------------------------------------
	
	protected void fireChange() {
		if (changeListeners != null) {
			changeListeners.fireChange(this);
		}
	}

	
	private ItemEditor addItemEditor() {
		int pos = innerPanel.getRowCount() - 1; 
		ItemEditor itemEditor = new ItemEditor();
		itemEditor.putAt(pos);
		items.add(itemEditor);
		return itemEditor;
	}
	
	
	
	private class ItemEditor implements ChangeListener, ClickListener {

		private TextBox textBox = new TextBox();
		private Hyperlink remove = new Hyperlink( messageSource.getMessage("textlist.remove"), "" );
		
		
		public ItemEditor() {
			textBox.addChangeListener(this);
			remove.addClickListener(this);			
		}
		
		
		public void putAt(int row) {
			innerPanel.insertRow(row);
			innerPanel.setWidget(row, 0, textBox);
			innerPanel.setWidget(row, 1, remove);	
		}
		
		
		public String getValue() {
			return textBox.getText();
		}
		
		
		public void setValue(String value) {
			textBox.setText(value);
		}
		
		
		public void onChange(Widget sender) {
			if (sender == textBox) {
				fireChange();
			}
		}	
		
		
		public void onClick(Widget sender) {
			if (sender == remove) {
				remove();
				items.remove(this);
				fireChange();
			}		
		}

		
		public void remove() {
			innerPanel.removeRow( getTableRow() );	
		}
		
		
		private int getTableRow() {
			for (int row = 0; row < innerPanel.getRowCount(); row++) {
				if (innerPanel.getWidget(row, 0) == textBox) {
					return row;
				}
			}
			return -1;
		}
	}
	
}
