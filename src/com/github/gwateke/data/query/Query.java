package com.github.gwateke.data.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.gwateke.data.query.Conjunction.And;




public class Query {

	public static String WHERE = "where";
	public static String ORDER_BY = "orderBy";
	public static String OFFSET = "offset";
	public static String LIMIT = "limit";
	
	private Criterion where;
	private List<Order> orderBy = new ArrayList<Order>();
	private int offset = -1;
	private int limit = -1;
	
	
	public Query() {
	}
	
	
	public Query(Criterion where) {
		this();
		addCriterion(where);
	}
	
	
	public Query(Order order) {
		this();
		addOrderBy(order);
	}
	
	
	public Criterion getWhere() {
		return where;
	}
	
	
	public void setWhere(Criterion where) {
		this.where = where;
	}
	
	
	public void addCriterion(Criterion criterion) {
		if (where == null) {
			setWhere(criterion);
		}
		else if (where instanceof And) {
			Conjunction and = where.clone();
			and.add(criterion);
			setWhere(and);
		}
		else {
			Conjunction and = new And();
			and.add(where);
			and.add(criterion);
			setWhere(and);
		}
	}
	
	
	public List<Order> getOrderBy() {
		return orderBy;
	}
	
	
	public void addOrderBy(Order order) {
		orderBy.add(order);
	}
	
	
	public void setOrderBy(Order order) {
		orderBy.clear();
		orderBy.add(order);
	}
	
	
	public int getOffset() {
		return offset;
	}
	
	
	public void setOffset(int offset) {
		this.offset = offset;
	}
	
	
	public int getLimit() {
		return limit;
	}
	
	
	public void setLimit(int limit) {
		this.limit = limit;
	}
	
	
	public Map<String, ?> toMap() {
		Map<String,Object> map = new HashMap<String, Object>();
		if (where != null) {
			map.put(WHERE, where.toMap());
		}
		if (!orderBy.isEmpty()) {
			map.put(ORDER_BY, ordersToMapList(orderBy));
		}
		if (offset != -1) {
			map.put(OFFSET, offset);
		}
		if (limit != -1) {
			map.put(LIMIT, limit);
		}
		return map;
	}
		
	
	private static List<Map<?,?>> ordersToMapList(List<Order> orders) {
		List<Map<?,?>> mapList = new ArrayList<Map<?,?>>();
		for (Order order: orders) {
			mapList.add(order.toMap());
		}		
		return mapList;
	}
}
