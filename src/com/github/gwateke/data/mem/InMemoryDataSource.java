/**
 * 
 */
package com.github.gwateke.data.mem;

import java.util.List;
import java.util.Map;

import com.github.gwateke.data.DataSource;
import com.github.gwateke.data.DataSourceAdapter;
import com.github.gwateke.data.query.Properties;
import com.github.gwateke.data.query.Query;

public class InMemoryDataSource extends DataSourceAdapter<Map<String, ?>, Object, Object> {
	private final List<Map<String,?>> data;
	private final String domainClassName;
	
	public InMemoryDataSource(List<Map<String, ?>> data, String domainClassName) {
		this.data = data;
		this.domainClassName = domainClassName;
	}
	
	public String getDomainClassName() {
		return domainClassName;
	}
	
	public void count(Query query, DataSource.Callback<Integer> callback) {
		callback.onSuccess(data.size());
	}
	
	public void list(Query query, Properties properties, DataSource.Callback<List<Map<String, ?>>> callback) {
		callback.onSuccess(data);
	}		
}