package com.github.gwateke.data;

import java.util.List;

import com.github.gwateke.data.query.Properties;
import com.github.gwateke.data.query.Query;
import com.github.gwateke.model.list.ListModel;



/**
 * @author juanjogarcia
 *
 * @param <T> domain object type.
 * @param <I> id type.
 * @param <E> error type.
 */
public interface DataSource<T, I> {

	String getDomainClassName();
	I getNullId();
	PropertyAccessor<T, I> getPropertyAccessor(T entity);
	ListModel<?, ?> createListModel(String detailType);
	void load(I id, Properties properties, Callback<T> callback);
	void save(T entity, Callback<?> callback);
	void delete(I[] ids, Callback<?> callback);
	<R> void invoke(String method, I[] ids, List<Object> args, Callback<R> callback);
	void list(Query query, Properties properties, Callback<List<T>> callback);
	void count(Query query, Callback<Integer> callback);
	
	interface Callback<R> {
		void onSuccess(R result, List<DataError> errors);
	}

}
