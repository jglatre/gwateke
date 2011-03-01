package com.github.gwateke.data.json;

import static com.google.gwt.http.client.RequestBuilder.GET;
import static com.google.gwt.http.client.RequestBuilder.POST;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.github.gwateke.core.closure.Closure;
import com.github.gwateke.data.DataError;
import com.github.gwateke.data.DataSource;
import com.github.gwateke.data.PropertyAccessor;
import com.github.gwateke.data.query.Comparison;
import com.github.gwateke.data.query.Conjunction;
import com.github.gwateke.data.query.Criterion;
import com.github.gwateke.data.query.Order;
import com.github.gwateke.data.query.Properties;
import com.github.gwateke.data.query.Query;
import com.github.gwateke.model.list.ListModel;
import com.github.gwateke.util.JSONUtil;
import com.google.gwt.http.client.Request;
import com.google.gwt.http.client.RequestBuilder;
import com.google.gwt.http.client.RequestCallback;
import com.google.gwt.http.client.RequestException;
import com.google.gwt.http.client.Response;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONParser;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;


public class JsonDataSource implements DataSource<JSONObject, Long> {

	private static final Long NULL_ID = -1L; // new JSONString("");

	private final String url;
	private final Closure<?, Throwable> errorHandler;
	private final String domainClassName;
	
	
	public JsonDataSource(String url, Closure<?, Throwable> errorHandler) {
		this(url, errorHandler, null);
	}
	
	
	public JsonDataSource(String url, Closure<?, Throwable> errorHandler, String domainClassName) {
		this.url = url;
		this.errorHandler = errorHandler;
		this.domainClassName = domainClassName;
	}
	
	
	public String getDomainClassName() {
		return domainClassName;
	}
	
	
	public Long getNullId() {
		return NULL_ID;
	}
	
	
	public PropertyAccessor<JSONObject, Long> getPropertyAccessor(JSONObject entity) {
		return new JsonPropertyAccessor( entity );
	}
	

	public ListModel<?, ?> createListModel(String detailType) {
		// TODO Auto-generated method stub
		return null;
	}
	

	public void save(JSONObject entity, Callback<?> callback) {
		// TODO Auto-generated method stub
	}


	public void delete(Long[] ids, final Callback<?> callback) {
		invoke("delete", ids, null, callback);
	}
	

	public <R> void invoke(String method, Long[] ids,	List<Object> args, Callback<R> callback) {
		// TODO se tendría que cambiar el parametro "ids" por un Query, de momento se hace la conversión
		Query query = new Query( new Comparison.In<Long>("id", Arrays.asList(ids)) );

		invoke(method, query, args, callback);
	}
	

	public void list(Query query, Properties properties, final Callback<List<JSONObject>> callback) {
		RequestBuilder request = new RequestBuilder( POST, url + "/list" );
		request.setRequestData( toJson(query).toString() );
		request.setCallback( new DefaultRequestCallback() {
			public void onResponseReceived(Request request, Response response) {
				JSONArray array = JSONParser.parseStrict( response.getText() ).isArray();
//				callback.onSuccess( array, null );
			}
		});
		doSend(request);
	}

	
	public void load(Long id, Properties properties, final Callback<JSONObject> callback) {
		String query = url + "/" + domainClassName + "/" + id.toString();
		RequestBuilder request = new RequestBuilder( GET, query );
		request.setHeader("Accept", "application/json");
		request.setCallback( new DefaultRequestCallback() {
			public void onResponseReceived(Request request, Response response) {
				JSONValue value = JSONParser.parseStrict( response.getText() );
				callback.onSuccess( value.isObject(), null );
			}
		});
		doSend(request);
	}
	
	
	public void count(Query query, final Callback<Integer> callback) {
		String q = url + "/" + domainClassName + "/count";
		RequestBuilder request = new RequestBuilder( GET, q );
		request.setHeader("Accept", "application/json");
//		request.setRequestData( toJson(query).toString() );
		request.setCallback( new DefaultRequestCallback() {
			public void onResponseReceived(Request request, Response response) {
				JSONValue value = JSONParser.parse( response.getText() );
				double count = value.isArray().get(0).isNumber().doubleValue();
				callback.onSuccess( Double.valueOf(count).intValue(), null );
			}
		});
		doSend(request);
	}


	public <R> void invoke(String method, Query query, List<Object> args,final Callback<R> callback) {

		RequestBuilder request = new RequestBuilder( POST, url + "/" + method );
		request.setRequestData( toJson(query).toString() );
		request.setCallback( new DefaultRequestCallback() {
			@SuppressWarnings("unchecked")
			public void onResponseReceived(Request request, Response response) {
				JSONValue result = JSONParser.parse( response.getText() );
				if (result != null && result.isObject() != null) {
					callback.onSuccess( (R) result.isObject(), null );
				}
				else if (result != null && result.isArray() != null) {
					callback.onSuccess( null, decodeErrors(result.isArray()) );
				}
				else {
					callback.onSuccess( null, Collections.<DataError>emptyList() );
				}
			}
		});
		doSend(request);
	}
	
	
	protected void doSend(RequestBuilder requestBuilder) {
		try {
			requestBuilder.send();
		} 
		catch (RequestException e) {
			errorHandler.call(e);
		}
	}
	
	
	protected class DefaultRequestCallback implements RequestCallback {
		public void onResponseReceived(Request request, Response response) {			
		}		

		public void onError(Request request, Throwable exception) {
			errorHandler.call(exception);
		}
	}

	//----------------------------------------------------------------------------------------------
	
	protected JSONObject toJson(Query query) {
		JSONObject json = new JSONObject();		
		final Criterion where = query.getWhere();
		
		if (where instanceof Conjunction) {
			for (Criterion criterion : (Conjunction) where) {
				addJson( json, (Comparison<?>) criterion );
			}
		}
		else if (where instanceof Comparison) {
			addJson( json, (Comparison<?>) where );
		}
		
		if (!query.getOrderBy().isEmpty()) {
			Order order = query.getOrderBy().get(0);
			json.put( "order", JSONUtil.toJsonValue( order.getField() ) );
			json.put( "asc", JSONUtil.toJsonValue( order.isAscending() ) );
		}
		if ( query.getOffset() != -1 ) {
			json.put( "offset", JSONUtil.toJsonValue( query.getOffset() ) );			
		}
		if ( query.getLimit() != -1 ) {
			json.put( "limit", JSONUtil.toJsonValue( query.getLimit() ) );
		}
		
		return json;
	}
	

	private void addJson(JSONObject json, Comparison<?> comparison) {
		String field = comparison.getField();
		Object value = comparison.getValue();
		json.put( field, JSONUtil.toJsonValue(value) );
	}
	
	
	private static List<DataError> decodeErrors(JSONArray array) {
		List<DataError> result = new ArrayList<DataError>();
		for (int i = 0; i < array.size(); i++) {
			JSONObject object = array.get(i).isObject();
			String field = object.get("field").isString().stringValue();
			List<String> codes = JSONUtil.toJavaList( object.get("codes").isArray() );
			List<?> arguments = JSONUtil.toJavaList( object.get("arguments").isArray() );
			result.add( new DataError( field, codes, arguments ) );
		}
		return result;
	}

	//----------------------------------------------------------------------------------------------
	
/*	protected String toUrl(Query query) {
		String url = "";
		final Criterion where = query.getWhere();
		
		if (where instanceof Conjunction) {
			for (Criterion criterion : (Conjunction) where) {
				url += toUrl( (Comparison<?>) criterion ) + '&';
			}
		}
		else if (where instanceof Comparison) {
			url += toUrl( (Comparison<?>) where ) + '&';
		}
		
		if (!query.getOrderBy().isEmpty()) {
			Order order = query.getOrderBy().get(0);
			url += "order=" + order.getField() + "&";
			url += "asc=" + order.isAscending() + "&";
		}
		url += "offset=" + query.getOffset() + "&";
		url += "limit=" + query.getLimit();
		return url;
	}*/
	
	
/*	protected String toUrl(Comparison<?> comparison) {
		final String field = comparison.getField();

		if (comparison instanceof Comparison.Equals) {
			String url = field + '=';
			Object value = comparison.getValue();
			if (value != null) {
				url += encodeValue(value);
			}
			return url;			
		}
		else if (comparison instanceof Comparison.In) {
			String url = "";
			for (Object item : ((Comparison.In<?>) comparison).getValue()) {
				url += field + '=' + encodeValue(item) + '&';
			}
			return url.endsWith("&") ? url.substring(0, url.length() - 1) : url;
		}
		else {
			throw new IllegalArgumentException("Tipo de comparación no soportada: " + comparison.getClass());
		}
	}*/


/*	protected static String encodeValue(Object value) {
		final String decodedValue;
		if (value instanceof Date) {
			decodedValue = formatDate((Date) value);
		}
		else if (value instanceof JSONString) {
			decodedValue = ((JSONString) value).stringValue();
		}
		else {
			decodedValue = String.valueOf(value);
		}
		return URL.encodeComponent( decodedValue );
	}*/
	
}
