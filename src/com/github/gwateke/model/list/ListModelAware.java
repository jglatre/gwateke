package com.github.gwateke.model.list;




/**
 * Indica que un widget necesita un <code>ListModel</code> para funcionar.
 * 
 * @author juanjogarcia
 */
public interface ListModelAware {
	void setListModel(ListModel<?, ?> model);
}
