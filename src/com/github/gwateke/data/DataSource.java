package com.github.gwateke.data;

import java.util.List;

import com.github.gwateke.data.query.Properties;
import com.github.gwateke.data.query.Query;
import com.github.gwateke.ui.list.model.ListModel;



/**
 * @author juanjogarcia
 *
 * @param <T> tipo de entidad.
 * @param <I> tipo de identificador.
 * @param <E> tipo de error.
 */
public interface DataSource<T, I, E> {

	String getDomainClassName();
	ListModel<?, ?> createListModel(String detailType);
	void load(I id, Properties properties, Callback<T> callback);
	void save(T entity, FallibleCallback<?, List<E>> callback);
	void delete(I[] ids, FallibleCallback<?, List<E>> callback);
	<R> void invoke(String method, I[] ids, List<Object> args, FallibleCallback<R, List<E>> callback);
	void list(Query query, Properties properties, Callback<List<T>> callback);
	void count(Query query, Callback<Integer> callback);
	
	interface Callback<R> {
		void onSuccess(R result);
	}

	interface FallibleCallback<R, F> extends Callback<R> {
		void onFailure(F failure);
	}
}
