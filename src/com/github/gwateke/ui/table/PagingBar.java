package com.github.gwateke.ui.table;

import java.util.Arrays;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import com.github.gwateke.context.Images;
import com.github.gwateke.context.MessageSource;
import com.github.gwateke.model.table.TableModel;
import com.github.gwateke.model.table.event.TableModelListener;
import com.github.gwateke.model.table.support.RowIdsSelectionHolder;
import com.github.gwateke.ui.Toolbar;
import com.github.gwateke.ui.ToolbarButton;
import com.google.gwt.event.dom.client.ChangeEvent;
import com.google.gwt.event.dom.client.ChangeHandler;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.TextBox;
import com.google.gwt.user.client.ui.Widget;


public class PagingBar extends Toolbar implements ClickHandler, TableModelListener, ListSelectionListener {
	
	private static final int[] PAGE_SIZES = {10, 20, 50, 100};

	private Label currentPage = new Label();
	private Label currentPage2 = new Label();
	private ListBox pageSizes = new ListBox();
	private Label sizeLabel = new Label();
	private TextBox pageNum = new TextBox();
	private ToolbarButton firstPage;
	private ToolbarButton prevPage;
	private ToolbarButton nextPage;
	private ToolbarButton lastPage;
	private Label selectInfo = new Label();
	private MessageSource messageSource;
	private TableModel<?> model;
	private boolean changing = false;
	
	
	public PagingBar(MessageSource messageSource, Images images) {
		super();
		setStyleName("toolbar");

		this.messageSource = messageSource;
		
		currentPage.setText( messageSource.getMessage("paging.current") );
		
		firstPage = new ToolbarButton(images.first());
		prevPage = new ToolbarButton(images.previous());
		nextPage = new ToolbarButton(images.next());
		lastPage = new ToolbarButton(images.last());
		
		firstPage.setTitle( messageSource.getMessage("paging.first") );
		prevPage.setTitle( messageSource.getMessage("paging.previous") );
		nextPage.setTitle( messageSource.getMessage("paging.next") );
		lastPage.setTitle( messageSource.getMessage("paging.last") );
		sizeLabel.setText( messageSource.getMessage("paging.size") );
				
		for (int i = 0; i < PAGE_SIZES.length; i++) {
			pageSizes.addItem(String.valueOf(PAGE_SIZES[i]));
		}
		 
		firstPage.addClickHandler(this);
		prevPage.addClickHandler(this);
		nextPage.addClickHandler(this);
		lastPage.addClickHandler(this);
		pageSizes.addChangeHandler( new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				model.setPageSize( PAGE_SIZES[pageSizes.getSelectedIndex()] );			 		
			}
		});
		
		pageNum.setSize("25px", "20px");
		
		// TODO usar binding
		pageNum.addChangeHandler( new ChangeHandler() {
			public void onChange(ChangeEvent event) {
				try {
					model.setPageNum(Integer.parseInt(pageNum.getText()) - 1);
				} 
				catch (NumberFormatException e) {
					model.setPageNum(0);
				}				
				pageNum.setText( String.valueOf(model.getPageNum() + 1) );
			}
		});
					
		setStyleName("toolbar");
		addItem(firstPage);
		addItem(prevPage);
		addItem(currentPage);
		addItem(pageNum);
		addItem(currentPage2);
		addItem(nextPage );
		addItem(lastPage );
		addItem(sizeLabel );
		addItem(pageSizes);
		addItem(selectInfo);
	}
	
	
	public void setModel(TableModel<?> model) {
		this.model = model;
		
		this.model.addListener(this);
		
		int index = Arrays.binarySearch(PAGE_SIZES, model.getPageSize());
		pageSizes.setSelectedIndex(index);	
		pageNum.setText( String.valueOf( model.getPageNum() + 1 ) );
	}
	
	
	public void onClick(ClickEvent event) {
		if (changing) {
			return;
		}
		
		Widget sender = (Widget) event.getSource();
		
		if (sender == firstPage) {
			model.setPageNum(0);
		}
		else if (sender == prevPage) {
			model.setPageNum( model.getPageNum() - 1 );
		}
		else if (sender == nextPage) {
			model.setPageNum( model.getPageNum() + 1 );
		}
		else if (sender == lastPage) {
			model.setPageNum( model.getPageCount() - 1 );
		}		
	}
	
	
	public void onDataChanging(TableModel<?> sender) {
		changing = true;
	}


	public void onDataChanged(TableModel<?> sender) {
		int pageNum = model.getPageNum() + 1;
		
		currentPage2.setText( messageSource.getMessage( "paging.total", model.getPageCount() ) );
		
		boolean first = (pageNum == 1);
		boolean last = (pageNum == model.getPageCount());
		
		firstPage.setEnabled(!first);
		prevPage.setEnabled(!first);
		nextPage.setEnabled(!last);
		lastPage.setEnabled(!last);
		
		this.pageNum.setText( String.valueOf(pageNum) );
		
		changing = false;
	}
	
	
	public void onPageSizeChanged(TableModel<?> model) {
	}

	
	public void onDataFilteringOrSorting(TableModel<?> sender) {
	}
	
	
	public void valueChanged(ListSelectionEvent event) {
		int count = ((RowIdsSelectionHolder<?>) event.getSource()).getSelectedRowCount();
		if (count > 0) {
			selectInfo.setVisible(true);
			selectInfo.setText( messageSource.getMessage("select.count", count) );
		}
		else {
			selectInfo.setVisible(false);
		}
	}

}
