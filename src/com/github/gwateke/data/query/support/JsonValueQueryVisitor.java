package com.github.gwateke.data.query.support;

import com.github.gwateke.data.query.Comparison;
import com.github.gwateke.data.query.Conjunction;
import com.github.gwateke.data.query.Property;
import com.github.gwateke.data.query.PropertySet;
import com.github.gwateke.data.query.Query;
import com.github.gwateke.data.query.QueryElement;
import com.github.gwateke.data.query.QueryVisitor;
import com.github.gwateke.util.JSONUtil;
import com.google.gwt.json.client.JSONArray;
import com.google.gwt.json.client.JSONNumber;
import com.google.gwt.json.client.JSONObject;
import com.google.gwt.json.client.JSONString;
import com.google.gwt.json.client.JSONValue;


public class JsonValueQueryVisitor implements QueryVisitor<JSONValue> {

	public static final String PROPERTIES = "properties";
	public static final String WHERE = "where";
	public static final String ORDER_BY = "orderBy";
	public static final String OFFSET = "offset";
	public static final String LIMIT = "limit";
	public static final String OPERATOR = "op";
	public static final String FIELD = "field";
	public static final String VALUE = "value";
	public static final String CRITERIA = "criteria";	

	
	public JSONValue visit(Query query) {
		JSONObject o = new JSONObject();
		if (query.getProperties() != null) {
			o.put(PROPERTIES, query.getProperties().accept(this));
		}
		if (query.getWhere() != null) {
			o.put( WHERE, query.getWhere().accept(this) );
		}
		if (query.getOffset() != -1) {
			o.put(OFFSET, new JSONNumber(query.getOffset()));
		}
		if (query.getLimit() != -1) {
			o.put(LIMIT, new JSONNumber(query.getLimit()));
		}
		return o;
	}

	
	public JSONValue visit(Comparison<?> comparison) {
		JSONObject o = new JSONObject();
		o.put( OPERATOR, new JSONString( comparison.getOperator() ) );
		o.put( FIELD, new JSONString( comparison.getField() ) );
		o.put( VALUE, JSONUtil.toJsonValue( comparison.getValue() ));
		return o;
	}

	
	public JSONValue visit(Conjunction conjunction) {
		JSONObject o = new JSONObject();
		o.put( OPERATOR, new JSONString( conjunction.getOperator() ) );
		o.put( CRITERIA, toArray( conjunction.getCriteria() ) );
		return null;
	}
	

	public JSONValue visit(Property property) {
		JSONObject o = new JSONObject();
		o.put( "name", new JSONString( property.getName() ) );
		o.put( "type", new JSONString( property.getType() ) );
		o.put( "subproperties", property.getSubproperties().accept(this) );
		return o;
	}

	
	public JSONValue visit(PropertySet propertySet) {
		return toArray(propertySet);
	}
	
	
	private JSONArray toArray(Iterable<? extends QueryElement> elements) {
		JSONArray a = new JSONArray();
		int i = 0;
		for (QueryElement element : elements) {
			a.set( i++, element.accept(this) );			
		}
		return a;
	}

}
