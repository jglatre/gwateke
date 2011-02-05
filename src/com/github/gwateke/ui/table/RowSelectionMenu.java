package com.github.gwateke.ui.table;

import com.github.gwateke.context.Images;
import com.github.gwateke.context.MessageSource;
import com.github.gwateke.model.table.TableSelectionModel;
import com.github.gwateke.model.table.support.DataTableModel;
import com.google.gwt.user.client.Command;
import com.google.gwt.user.client.ui.MenuBar;
import com.google.gwt.user.client.ui.MenuItem;


public class RowSelectionMenu extends MenuBar {

	private TableSelectionModel<?> selectionModel;
	private DataTableModel<?> tableModel;
	private MenuBar submenu = new MenuBar(true);
	
	
	public RowSelectionMenu(MessageSource messageSource, Images images) {
		super();
		setAutoOpen(true);
		setAnimationEnabled(true);
		
		submenu.addItem( messageSource.getMessage("select.all"), new SelectPageCommand() );
		submenu.addItem( messageSource.getMessage("select.none"), new ClearPageSelectionCommand() );

		MenuItem item = addItem( images.next().getHTML(), true, submenu );
		item.setTitle( messageSource.getMessage("select") );
	}

	
	public void setSelectionModel(TableSelectionModel<?> selectionModel) {
		this.selectionModel = selectionModel;
	}	
	
	
	public void setTableModel(DataTableModel<?> tableModel) {
		this.tableModel = tableModel;
	}
	
	
	private class SelectPageCommand implements Command {
		public void execute() {
			selectionModel.addSelection( tableModel.getPageFirstRow(), tableModel.getPageMaxRow() - 1 );
		}		
	}
	
	
	private class ClearPageSelectionCommand implements Command {
		public void execute() {	
			selectionModel.removeSelection( tableModel.getPageFirstRow(), tableModel.getPageMaxRow() - 1 );
		}			
	}

}
