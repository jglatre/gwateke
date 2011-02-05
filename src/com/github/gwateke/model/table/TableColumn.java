package com.github.gwateke.model.table;


public class TableColumn {

	private String id;
	private String type;
//	private String title;
//	private String description;
	private String orderBy;
	private boolean sortable = false;
	
	
	public TableColumn(String id, String type,/* String title, String description,*/ String orderBy, boolean sortable) {
		this.id = id;
		this.type = type;
//		this.title = title;
//		this.description = description;
		this.orderBy = orderBy;
		this.sortable = sortable;
	}
		
	public String getId() {
		return id;
	}
	
	public String getType() {
		return type;
	}
	
//	public String getTitle() {
//		return title;
//	}
	
//	public String getDescription() {
//		return description;
//	}
	
	public String getOrderBy() {
		return orderBy;
	}

	public boolean isSortable() {
		return sortable;
	}
}
