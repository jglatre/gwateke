package com.github.gwateke.binding.adapter;

import java.util.ArrayList;
import java.util.List;

import com.github.gwateke.binding.Binding;
import com.github.gwateke.binding.value.ValueModel;
import com.github.gwateke.ui.list.model.ListModel;
import com.github.gwateke.ui.list.model.ListModelListener;
import com.google.gwt.user.client.ui.ListBox;


public class ListBoxBinding implements Binding<ListBox> {

	private ListBox listBox; 
	private ValueModel<Object> selectionHolder;
	private SelectionInList<Object> selectionInList;
	private Binding<ListBox> indexBinding;
	private ListModelListener listChangeHandler = new ListChangeHandler();
	
	
	@SuppressWarnings("unchecked")
	public ListBoxBinding(ListBox listBox, ValueModel<?> selectionHolder, ListModel<?, ?> model) {
		this.listBox = listBox;
		this.selectionHolder = (ValueModel<Object>) selectionHolder;
		model.addListener( listChangeHandler );

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

	private class ListChangeHandler implements ListModelListener {
		public void onDataChanged(ListModel<?, ?> model) {
			unbind();
			initialize(model);
		}					
	}
	
	
	private void loadListBox(ListModel<?, ?> model) {
		listBox.clear();
		for (int i = 0; i < model.getSize(); i++) {
			listBox.addItem( String.valueOf(model.getElementAt(i)) );
		}
//		for (Iterator it = items.iterator(); it.hasNext();) {
//			listBox.addItem( String.valueOf(it.next()) );			
//		}
	}
	
	
	private List<Object> listKeys(ListModel<?, ?> model) {
		List<Object> keys = new ArrayList<Object>();
		for (int i = 0; i < model.getSize(); i++) {
			keys.add( model.getKeyAt(i) );
		}
		return keys;
	}
}
