package com.github.gwateke.data.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


public class Conjunction implements Criterion, Iterable<Criterion> {

	public static final String CRITERIA = "criteria";
	
	public static final String AND = "and";
	public static final String OR = "or";
	
	private String operator;
	private List<Criterion> criteria = new ArrayList<Criterion>();
	
	
	public Conjunction(String operator) {
		if (!(AND.equals(operator) || OR.equals(operator))) {
			throw new IllegalArgumentException("Invalid operator " + operator);
		}
		
		this.operator = operator;
	}
	
	
	public Conjunction(String operator, Criterion... criteria) {
		this(operator);
		
		for (Criterion criterion : criteria) {
			add(criterion);
		}
	}
	
	
	public String getOperator() {
		return operator;
	}
	
	
	public List<Criterion> getCriteria() {
		return criteria;
	}
	
	
	public void add(Criterion criterion) {
		criteria.add(criterion);
	}
	
	
	public String toString() {
		String separator = ' ' + getOperator() + ' ';
		String result = "(";
		for (Iterator<?> it = getCriteria().iterator(); it.hasNext();) {
			result += it.next();
			if (it.hasNext()) {
				result += separator;
			}
		}
		result += ")";
		return result;
	}
	

	public Iterator<Criterion> iterator() {
		return getCriteria().iterator();
	}

	
	@SuppressWarnings("unchecked")
	public Conjunction clone() {
		Conjunction clon = new Conjunction(this.operator);
		for (Criterion criterion: this) {
			clon.add( criterion.clone() );
		}		
		return clon;
	}
	
	
	public Map<?,?> toMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(OPERATOR, operator);
		map.put(CRITERIA, criteriaToMapList(criteria));
		return map;
	}
	
	
	public static class And extends Conjunction {
		public And(Criterion... criteria) {
			super(AND, criteria);
		}
	}

	
	public static class Or extends Conjunction {
		public Or(Criterion... criteria) {
			super(OR, criteria);
		}
	}	
	
	
	private static List<Map<?,?>> criteriaToMapList(List<Criterion> criteria) {
		List<Map<?,?>> mapList = new ArrayList<Map<?,?>>();
		for (Criterion criterion: criteria) {
			mapList.add( criterion.toMap() );
		}		
		return mapList;
	}

}
