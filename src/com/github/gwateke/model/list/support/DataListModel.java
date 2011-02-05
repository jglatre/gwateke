package com.github.gwateke.model.list.support;

import java.util.List;

import com.github.gwateke.core.closure.Closure;
import com.github.gwateke.data.DataError;
import com.github.gwateke.data.DataSource;
import com.github.gwateke.data.PropertyAccessor;
import com.github.gwateke.data.DataSource.Callback;
import com.github.gwateke.data.query.Criterion;
import com.github.gwateke.data.query.Properties;
import com.github.gwateke.data.query.Query;
import com.github.gwateke.model.list.event.ListModelChangeHandler;
import com.google.gwt.event.shared.HandlerRegistration;



/**
 * 
 * 
 * @author juanjo
 */
public class DataListModel<T> extends AbstractListModel<Object, String> {

	private static final String DEFAULT_KEY_PROPERTY = "id";
	private static final String DEFAULT_DESCR_PROPERTY = "name";
	
	private DataSource<T, ?> dataSource;
	private String keyProperty = DEFAULT_KEY_PROPERTY;
	private String descriptionProperty = DEFAULT_DESCR_PROPERTY;
	private Properties properties;
	private Criterion baseFilter;
	private List<T> elements;
	private Closure<String, T> elementBuilder;
	
	
	public DataListModel(DataSource<T, ?> dataSource) {
		this.dataSource = dataSource;		
		setNullKey( dataSource.getNullId() );
		setNullElement("");
	}
	
	
	public DataListModel(DataSource<T, ?> dataSource, Closure<String, T> elementBuilder) {
		this(dataSource);
		this.elementBuilder = elementBuilder;
	}
	
	
	public DataListModel(DataSource<T, ?> dataSource, String descriptionProperty) {
		this(dataSource);
		this.descriptionProperty = descriptionProperty;
	}
	
	
	public DataListModel(DataSource<T, ?> dataSource, String keyProperty, String descriptionProperty) {
		this(dataSource, descriptionProperty);
		this.keyProperty = keyProperty;
	}
	
	
	public DataListModel(DataSource<T, ?> dataSource, String descriptionProperty, boolean hasNull) {
		this(dataSource, descriptionProperty);
		setNullable(hasNull);
	}
	
	
	public Closure<String, T> getElementBuilder() {
		if (elementBuilder == null) {
			elementBuilder = new DefaultElementBuilder();
		}
		return elementBuilder;
	}
	
	
	public int getSize() {
		int size = elements != null ? elements.size() : 0;
		if (isNullable()) {
			size++;
		}
		return size;
	}
	
	
	public String getElementAt(int index) {
		if (isNullable()) {
			return index == 0 ? getNullElement() : getElementBuilder().call( elements.get(index - 1) );
		}
		else {
			return getElementBuilder().call( elements.get(index) );
		}
	}
	
	
	public Object getKeyAt(int index) {
		if (isNullable()) {
			PropertyAccessor<T, ?> accessor = dataSource.getPropertyAccessor( elements.get(index - 1) );
			return index == 0 ? getNullKey() : accessor.get(keyProperty);
		}
		else {
			PropertyAccessor<T, ?> accessor = dataSource.getPropertyAccessor( elements.get(index) );
			return accessor.get(keyProperty);
		}		
	}
	

	public HandlerRegistration addChangeHandler(ListModelChangeHandler handler) {
		HandlerRegistration registration = super.addChangeHandler(handler);
		
		if (elements == null) {
			refresh();
		}
		return registration;
	}
	
//	public void addListener(ListModelListener listener) {
//		super.addListener(listener);
//		
//		if (elements == null) {
//			refresh();
//		}
//	}
	
	
	public Properties getProperties() {
		if (properties == null) {
			properties = createProperties();			
		}
		return properties;
	}	
	
	   
    public void setBaseFilter(Criterion baseFilter) {
    	this.baseFilter = baseFilter;
    }

	
	public void refresh() {
		Query query = new Query();
		
		if (baseFilter != null) {
			query.setWhere(baseFilter);
		}

		dataSource.list(query, getProperties(), new Callback<List<T>>() {
			public void onSuccess(List<T> result, List<DataError> errors) {
				elements = result;
				fireDataChanged();
			}			
		});
	}
	
	//----------------------------------------------------------------------------------------------	
	
	protected Properties createProperties() {
		Properties properties = new Properties();
		properties.add(keyProperty);
		properties.add(descriptionProperty);
    	return properties;
	}
	
	
	protected class DefaultElementBuilder implements Closure<String, T> {
		public String call(T item) {
			PropertyAccessor<T, ?> accessor = dataSource.getPropertyAccessor( item );
			return (String) accessor.get(descriptionProperty);
		}
	}
	
}
