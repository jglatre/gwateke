package com.github.gwateke.ui.list.model;




public interface ListModelResolver {
	ListModel<?, ?> getListModel(String listName);
}
