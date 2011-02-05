package com.github.gwateke.model.support;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.github.gwateke.core.closure.Closure;
import com.github.gwateke.data.DataError;
import com.github.gwateke.data.DataSource;
import com.github.gwateke.data.query.Properties;
import com.github.gwateke.data.query.Query;
import com.google.gwt.user.client.ui.SuggestOracle;


/**
 * Implementación de SuggestOracle que obtiene sus sugerencias de un DataSource.
 * 
 * @author juanjogarcia
 */
public abstract class DataSourceSuggestOracle<T> extends SuggestOracle {

	private DataSource<T, ?> dataSource;
	private Closure<?, List<T>> resultsCallback;

	
	protected DataSourceSuggestOracle(DataSource<T, ?> dataSource) {
		this.dataSource = dataSource;
	}
	
	    
	public void setResultsCallback(Closure<?, List<T>> resultsCallback) {
		this.resultsCallback = resultsCallback;
	}


	public boolean isDisplayStringHTML() {
		return true;
	}

	
	/**
	 * Carga {@link Suggestion}s mediante una consulta al servidor.
	 * 
	 * @see com.google.gwt.user.client.ui.SuggestOracle#requestSuggestions(com.google.gwt.user.client.ui.SuggestOracle.Request, com.google.gwt.user.client.ui.SuggestOracle.Callback)
	 */
	@Override
	public void requestSuggestions(final Request request, final Callback callback) {
		final String queryString = request.getQuery();  //.toLowerCase();

		Query query = createQuery(request);
		
		dataSource.list(query, createResultProperties(), new DataSource.Callback<List<T>>() {
			public void onSuccess(List<T> result, List<DataError> errors) {
				if (resultsCallback != null) {
					resultsCallback.call(result);
				}
				callback.onSuggestionsReady( request, new Response(createSuggestions(result, queryString)) );
			}						
		});
	}
	
	
	protected Collection<Suggestion> createSuggestions(List<T> results, String queryString) {
		List<Suggestion> suggestions = new ArrayList<Suggestion>();
		for (T result : results) {
			suggestions.add( createSuggestion(result, queryString) );
		}
		return suggestions;
	}


	protected abstract Query createQuery(Request request);
	
	protected abstract Suggestion createSuggestion(T result, String queryString);
	
	protected abstract Properties createResultProperties();

	
	protected static class QueryHighlightingSuggestion implements Suggestion {
		private final String text;
		private final String highlighted;
		
		public QueryHighlightingSuggestion(String text, String query) {
			this.text = text;
			this.highlighted = highlight(text, query);
		}
		
		public String getDisplayString() {
			return highlighted;
		}

		public String getReplacementString() {
			return text;
		}
		
		private static String highlight(String target, String query) {
			StringBuffer buf = new StringBuffer(target);
			int pos = target.toLowerCase().indexOf(query.toLowerCase());
			buf.insert(pos + query.length(), "</strong>");
			buf.insert(pos, "<strong>");
			return buf.toString();
		}
	}

}
