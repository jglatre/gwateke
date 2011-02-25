package com.github.gwateke.data.query;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class Conjunction implements Criterion, Iterable<Criterion> {

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
	
	
	public <R> R accept(QueryVisitor<R> visitor) {
		return visitor.visit(this);
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

}
