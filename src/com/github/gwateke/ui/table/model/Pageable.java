package com.github.gwateke.ui.table.model;


public interface Pageable {
	
	int getPageNum();
	void setPageNum(int pageNum);
	int getPageCount();
	int getPageSize();
	void setPageSize(int pageSize);
	int getPageRowCount();
}
