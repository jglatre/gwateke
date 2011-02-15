package com.github.gwateke.data;

import java.util.List;

import com.github.gwateke.data.query.Properties;
import com.github.gwateke.data.query.Query;
import com.github.gwateke.model.list.ListModel;


public class DataSourceAdapter<T, I> implements DataSource<T, I> {

	public void count(Query query, DataSource.Callback<Integer> callback) {
	}

	public ListModel<?, ?> createListModel(String detailType) {
		return null;
	}

	public void delete(I[] ids, DataSource.Callback<?> callback) {
	}

	public String getDomainClassName() {
		return null;
	}

	public <R> void invoke(String method, I[] ids, List<Object> args, DataSource.Callback<R> callback) {
	}

	public void list(Query query, Properties properties, DataSource.Callback<List<T>> callback) {
	}

	public void load(I id, Properties properties, DataSource.Callback<T> callback) {
	}

	public void save(T entity, DataSource.Callback<?> callback) {
	}

	public I getNullId() {
		return null;
	}

	public PropertyAccessor<T, I> getPropertyAccessor(T entity) {
		return null;
	}
}
