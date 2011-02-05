package com.github.gwateke.model.table;


public interface Pageable {
	
	int getPageNum();
	void setPageNum(int pageNum);
	int getPageCount();
	int getPageSize();
	void setPageSize(int pageSize);
	int getPageRowCount();
}
