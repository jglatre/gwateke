package com.github.gwateke.ui.list.model;



/**
 * Indica que un widget necesita un <code>ListModel</code> para funcionar.
 * 
 * @author juanjogarcia
 */
public interface ListModelDependent {
	void setListModel(ListModel<?, ?> model);
}
