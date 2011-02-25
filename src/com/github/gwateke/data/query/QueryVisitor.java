package com.github.gwateke.data.query;



/**
 * @author juanjo
 * @param <R> result type.
 */
public interface QueryVisitor<R> {

	R visit(Query query);
	R visit(Comparison<?> comparison);
	R visit(Conjunction conjunction);
	R visit(Property property);
	R visit(PropertySet propertySet);
	R visit(Order order);
}