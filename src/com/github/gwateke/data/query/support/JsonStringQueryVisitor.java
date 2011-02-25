package com.github.gwateke.data.query.support;

import com.github.gwateke.data.query.Comparison;
import com.github.gwateke.data.query.Conjunction;
import com.github.gwateke.data.query.Property;
import com.github.gwateke.data.query.PropertySet;
import com.github.gwateke.data.query.Query;
import com.github.gwateke.data.query.QueryVisitor;


public class JsonStringQueryVisitor implements QueryVisitor<String> {

	private JsonValueQueryVisitor jsonValueVisitor = new JsonValueQueryVisitor();
	
	
	public String visit(Query query) {
		return query.accept(jsonValueVisitor).toString();
	}
	

	public String visit(Comparison<?> comparison) {
		return comparison.accept(jsonValueVisitor).toString();
	}

	
	public String visit(Conjunction conjunction) {
		return conjunction.accept(jsonValueVisitor).toString();
	}

	
	public String visit(Property property) {
		return property.accept(jsonValueVisitor).toString();
	}


	public String visit(PropertySet propertySet) {
		return propertySet.accept(jsonValueVisitor).toString();
	}

}
