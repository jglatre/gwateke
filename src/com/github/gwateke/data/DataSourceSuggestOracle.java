package com.github.gwateke.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.github.gwateke.core.closure.Closure;
import com.github.gwateke.data.query.Properties;
import com.github.gwateke.data.query.Query;
import com.google.gwt.user.client.ui.SuggestOracle;


/**
 * Implementación de SuggestOracle que obtiene sus sugerencias de un DataSource.
 * 
 * @author juanjogarcia
 */
public abstract class DataSourceSuggestOracle extends SuggestOracle {

	private DataSource<Map<String,?>, ?, ?> dataSource;
	private Closure<?, List<Map<String,?>>> resultsCallback;

	
	protected DataSourceSuggestOracle(DataSource<Map<String,?>, ?, ?> dataSource) {
		this.dataSource = dataSource;
	}
	
	    
	public void setResultsCallback(Closure<?, List<Map<String,?>>> resultsCallback) {
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
		
		dataSource.list(query, createResultProperties(), new DataSource.Callback<List<Map<String,?>>>() {
			public void onSuccess(List<Map<String,?>> result) {
				if (resultsCallback != null) {
					resultsCallback.call(result);
				}
				callback.onSuggestionsReady( request, new Response(createSuggestions(result, queryString)) );
			}						
		});
	}
	
	
	protected Collection<Suggestion> createSuggestions(List<Map<String,?>> results, String queryString) {
		List<Suggestion> suggestions = new ArrayList<Suggestion>();
		for (Map<String,?> result : results) {
			suggestions.add( createSuggestion(result, queryString) );
		}
		return suggestions;
	}


	protected abstract Query createQuery(Request request);
	
	protected abstract Suggestion createSuggestion(Map<String, ?> result, String queryString);
	
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
