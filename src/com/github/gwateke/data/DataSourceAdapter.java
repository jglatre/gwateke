package com.github.gwateke.data;

import java.util.List;

import com.github.gwateke.data.query.Properties;
import com.github.gwateke.data.query.Query;
import com.github.gwateke.ui.list.model.ListModel;


public class DataSourceAdapter<T, I, E> implements DataSource<T, I, E> {

	public void count(Query query, DataSource.Callback<Integer> callback) {
	}

	public ListModel<?, ?> createListModel(String detailType) {
		return null;
	}

	public void delete(I[] ids, DataSource.FallibleCallback<?, List<E>> callback) {
	}

	public String getDomainClassName() {
		return null;
	}

	public <R> void invoke(String method, I[] ids, List<Object> args, DataSource.FallibleCallback<R, List<E>> callback) {
	}

	public void list(Query query, Properties properties, DataSource.Callback<List<T>> callback) {
	}

	public void load(I id, Properties properties, DataSource.Callback<T> callback) {
	}

	public void save(T entity, DataSource.FallibleCallback<?, List<E>> callback) {
	}

}
