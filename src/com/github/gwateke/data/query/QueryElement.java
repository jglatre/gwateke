package com.github.gwateke.data.query;

public interface QueryElement {

	<R> R accept(QueryVisitor<R> visitor);
}
