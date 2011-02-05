package com.github.gwateke.binding.adapter;

import java.util.ArrayList;
import java.util.List;

import com.github.gwateke.binding.Binding;
import com.github.gwateke.binding.value.ValueModel;
import com.github.gwateke.model.list.ListModel;
import com.github.gwateke.model.list.event.ListModelChangeEvent;
import com.github.gwateke.model.list.event.ListModelChangeHandler;
import com.google.gwt.user.client.ui.ListBox;


public class ListBoxBinding implements Binding<ListBox> {

	private ListBox listBox; 
	private ValueModel<Object> selectionHolder;
	private SelectionInList<Object> selectionInList;
	private Binding<ListBox> indexBinding;
	private DataChangeHandler dataChangeHandler = new DataChangeHandler();
	
	
	@SuppressWarnings("unchecked")
	public ListBoxBinding(ListBox listBox, ValueModel<?> selectionHolder, ListModel<?, ?> model) {
		this.listBox = listBox;
		this.selectionHolder = (ValueModel<Object>) selectionHolder;
		model.addChangeHandler( dataChangeHandler );

		if (model.getSize() > 0) {
			initialize(model);
		}
	}
	
	
	public ListBox getWidget() {
		return listBox;
	}
	
	
	protected void initialize(ListModel<?, ?> model) {
		loadListBox(model);
		selectionInList = new SelectionInList<Object>( listKeys(model), selectionHolder );
		indexBinding = new ListBoxIndexAdapter(listBox, selectionInList.getSelectionIndexHolder());
	}
	

	public void unbind() {
		if (selectionInList != null) {
			selectionInList.release();
		}
		if (indexBinding != null) {
			indexBinding.unbind();
		}
	}

	//---------------------------------------------------------------

	private class DataChangeHandler implements ListModelChangeHandler {
		public void onChange(ListModelChangeEvent event) {
			unbind();
			initialize( (ListModel<?, ?>) event.getSource() );
		}					
	}
	
	
	private void loadListBox(ListModel<?, ?> model) {
		listBox.clear();
		for (int i = 0; i < model.getSize(); i++) {
			listBox.addItem( String.valueOf(model.getElementAt(i)) );
		}
	}
	
	
	private List<Object> listKeys(ListModel<?, ?> model) {
		List<Object> keys = new ArrayList<Object>();
		for (int i = 0; i < model.getSize(); i++) {
			keys.add( model.getKeyAt(i) );
		}
		return keys;
	}
}
